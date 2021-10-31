//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Config.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.config;

import moe.kawayi.org.utopia.core.util.NotNull;
import moe.kawayi.org.utopia.core.util.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * 线程安全的配置文件接口
 */
public interface Config {

    /**
     * 导航路径
     */
    interface NavigationPath{}

    /**
     * 创建新导航路径
     * @param paths 导航路径的字符串
     * @return 导航路径
     * @exception Exception 路径不合法
     */
    NavigationPath createPath(@NotNull String... paths) throws Exception;

    /**
     * 获取byte
     * @param path 导航路径。如果为null，则将this视为要获取byte的对象
     * @return 如果路径不存在，或者类型不正确，返回empty
     * @throws Exception 一些实现可能会在路径不存在或者类型不正确时直接抛出异常而非返回empty
     */
    Optional<Byte> getByte(@Nullable NavigationPath path) throws Exception;

    /**
     * @see Config#getByte(NavigationPath)
     */
    Optional<Short> getShort(@Nullable NavigationPath path) throws Exception;

    /**
     * @see Config#getByte(NavigationPath)
     */
    Optional<Integer> getInt(@Nullable NavigationPath path) throws Exception;

    /**
     * @see Config#getByte(NavigationPath)
     */
    Optional<Long> getLong(@Nullable NavigationPath path) throws Exception;
    /**
     * @see Config#getByte(NavigationPath)
     */

    Optional<Boolean> getBoolean(@Nullable NavigationPath path) throws Exception;

    /**
     * @see Config#getByte(NavigationPath)
     */
    Optional<Float> getFloat(@Nullable NavigationPath path) throws Exception;

    /**
     * @see Config#getByte(NavigationPath)
     */
    Optional<Double> getDouble(@Nullable NavigationPath path) throws Exception;

    /**
     * @see Config#getByte(NavigationPath)
     */
    Optional<String> getString(@Nullable NavigationPath path) throws Exception;

    /**
     * @see Config#getByte(NavigationPath)
     */
    Optional<UUID> getUUID(@Nullable NavigationPath path) throws Exception;

    /**
     * @see Config#getByte(NavigationPath)
     */
    Optional<List<Config>> getArray(@Nullable NavigationPath path) throws Exception;

    /**
     * @see Config#getByte(NavigationPath)
     */
    Optional<Config> getObject(@Nullable NavigationPath path) throws Exception;

    /**
     * 渲染此配置文件到字符串
     * @return 字符串
     */
    @NotNull
    String renderToString();
}