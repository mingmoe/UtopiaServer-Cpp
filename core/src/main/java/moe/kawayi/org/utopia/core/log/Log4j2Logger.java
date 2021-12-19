//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The Log4j2Logger.java is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

package moe.kawayi.org.utopia.core.log;

import moe.kawayi.org.utopia.core.util.NotNull;

import java.util.Objects;

/**
 * Log4j2的logger
 */
public class Log4j2Logger implements Logger{

    private final org.apache.logging.log4j.Logger logger;

    /**
     * 使用Log4j2的日志器进行初始化
     * @param logger 要使用的日志器
     */
    public Log4j2Logger(@NotNull org.apache.logging.log4j.Logger logger){
        Objects.requireNonNull(logger);
        this.logger = logger;
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public void trace(String msg) {
        logger.trace(msg);
    }

    @Override
    public void trace(String msg, Object[] objects) {
        logger.trace(msg,objects);
    }

    @Override
    public void trace(String msg, Exception exception) {
        logger.trace(msg,exception);
    }

    @Override
    public void debug(String msg) {
        logger.debug(msg);
    }

    @Override
    public void debug(String msg, Object[] objects) {
        logger.debug(msg,objects);
    }

    @Override
    public void debug(String msg, Exception exception) {
        logger.debug(msg,exception);
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void info(String msg, Object[] objects) {
        logger.info(msg,objects);
    }

    @Override
    public void info(String msg, Exception exception) {
        logger.info(msg,exception);
    }

    @Override
    public void warn(String msg) {
        logger.warn(msg);
    }

    @Override
    public void warn(String msg, Object[] objects) {
        logger.warn(msg,objects);
    }

    @Override
    public void warn(String msg, Exception exception) {
        logger.warn(msg,exception);
    }

    @Override
    public void error(String msg) {
        logger.error(msg);
    }

    @Override
    public void error(String msg, Object[] objects) {
        logger.error(msg,objects);
    }

    @Override
    public void error(String msg, Exception exception) {
        logger.error(msg,exception);
    }
}