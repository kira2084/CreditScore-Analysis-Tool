package com.ms.credit.config;


import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.HttpAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.JsonLayout;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.net.URL;


@Component
public class SplunkConfig {


    @PostConstruct
    public void setupLogger() {
        try {
            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            final Configuration config = ctx.getConfiguration();

            JsonLayout layout = JsonLayout.newBuilder()
                .setConfiguration(config)
                .setCompact(true).setEventEol(true).build();

            URL splunkUrl = new URL("http://your-splunk-server:8088/services/collector");

            HttpAppender appender = HttpAppender.newBuilder()
                .setName("HttpAppender")
                .setUrl(splunkUrl)
                .setLayout(layout).setConfiguration(config)
                .build();

            appender.start();
            config.addAppender(appender);
            config.getRootLogger().addAppender(appender, null, null);
            ctx.updateLoggers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

