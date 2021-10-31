//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The NetMain.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import moe.kawayi.org.utopia.core.config.Config;
import moe.kawayi.org.utopia.core.config.ConfigManager;
import moe.kawayi.org.utopia.core.resource.ResourceLoaderBase;
import moe.kawayi.org.utopia.core.resource.ResourceManager;
import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 设置网络
 */
public final class NetMain {

    /**
     * 配置文件路径
     */
    private static final String CONFIG_PATH = "config/internet.conf";

    /**
     * 日志器
     */
    private static final Logger LOGGER = LogManager.getLogger(NetMain.class);

    /**
     * 服务器运行状态
     */
    private static final AtomicBoolean IS_RUNNING = new AtomicBoolean(false);

    /**
     * 工作组
     */
    @NotNull
    private static final AtomicReference<EventLoopGroup> BOSS_GROUP = new AtomicReference<>(null);

    /**
     * 工作组
     */
    @NotNull
    private static final AtomicReference<EventLoopGroup> WORKER_GROUP = new AtomicReference<>(null);

    @NotNull
    private static final AtomicReference<Config> CONFIG = new AtomicReference<>();

    /**
     * 获取网络服务器配置文件
     * @return 网络服务器配置文件。如果未加载则返回empty
     */
    public static Optional<Config> getInternetConfig(){
        return Optional.ofNullable(CONFIG.get());
    }

    /**
     * 创建默认配置文件
     */
    private static Config createDefaultConfiguration(@NotNull Path path) throws Exception {
        Objects.requireNonNull(path);

        if(!path.toFile().exists())
            Files.writeString(
                path,
                ConfigManager.createDefaultHocon(NetConfig.class),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

        return ConfigManager.loadConfig(path).orElseThrow();
    }

    /**
     * 启动网络系统
     *
     * @throws InterruptedException 线程中断
     */
    public static void internetBootstrap() throws Exception {
        // never run again
        if (IS_RUNNING.getAndSet(true))
            return;

        // 处理配置
        var config = ConfigManager.loadConfig(Path.of(CONFIG_PATH)).orElse(createDefaultConfiguration(Path.of(CONFIG_PATH)));

        // 获取设置
        int boosThreadCount =
                        config.getInt(config.createPath(NetConfig.NETTY_BOOS_THREAD_COUNT))
                        .orElse(NetConfig.NETTY_BOOS_THREAD_COUNT_DEFAULT);

        int workerThreadCount =
                                config.getInt(config.createPath(NetConfig.NETTY_WORKER_THREAD_COUNT))
                        .orElse(NetConfig.NETTY_WORKER_THREAD_COUNT_DEFAULT);

        int port =
                                config.getInt(config.createPath(NetConfig.PORT))
                        .orElse(NetConfig.PORT_DEFAULT);

        int maxWaitList =
                                config.getInt(config.createPath(NetConfig.MAX_WAIT_LIST))
                        .orElse(NetConfig.MAX_WAIT_LIST_DEFAULT);

        CONFIG.set(config);

        // 初始化事件循环线程
        BOSS_GROUP.set(
                new NioEventLoopGroup(
                        boosThreadCount,
                        new NetThreadFactory("boos-thread-")));

        WORKER_GROUP.set(
                new NioEventLoopGroup(
                        workerThreadCount,
                        new NetThreadFactory("worker-thread-")));

        // 设置启动类
        ServerBootstrap b = new ServerBootstrap();

        b.group(BOSS_GROUP.get(), WORKER_GROUP.get())
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyChannelInit())
                .option(ChannelOption.SO_BACKLOG, maxWaitList)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture f = b.bind(port).sync();

        LOGGER.info("网络服务器启动");

        // 设置关闭动作
        f.channel().closeFuture().addListener((ChannelFutureListener) channelFuture -> {
            LOGGER.info("网络服务器关闭");
        });
    }

    /**
     * 获取netty是否在运行
     *
     * @return 如果返回true，则说明netty还在运行。
     */
    public static boolean isRun() {
        return IS_RUNNING.get();
    }

    /**
     * 关闭netty服务器
     */
    public static void shutdown() {
        if (IS_RUNNING.getAndSet(false)) {
            var group = BOSS_GROUP.getAndSet(null);

            if (group != null && (!group.isShutdown()) && (!group.isTerminated()) && (!group.isShuttingDown()))
                group.shutdownGracefully();

            group = WORKER_GROUP.getAndSet(null);

            if (group != null && (!group.isShutdown()) && (!group.isTerminated()) && (!group.isShuttingDown()))
                group.shutdownGracefully();
        }
    }

}