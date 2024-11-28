package com.springcloud.ecommerce.gateway.filters;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class CookieGatewayFilterFactory extends AbstractGatewayFilterFactory<ConfigurationCookie> {

    private final Logger logger = LoggerFactory.getLogger(CookieGatewayFilterFactory.class);

    public CookieGatewayFilterFactory() {
        super(ConfigurationCookie.class);
    }

    @Override
    public GatewayFilter apply(ConfigurationCookie config) {
        return (exchange, chain) -> {
            logger.info("before gateway filter factory: " + config.getMessage());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                Optional.ofNullable(config.getValue()).ifPresent(cookie -> {
                    exchange.getResponse().addCookie(ResponseCookie.from(config.getName(), cookie).build());
                });

                logger.info("after gateway filter factory: " + config.getMessage());
            }));
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("message", "name", "value");
    }

    @Override
    public String name() {
        return "CookieGateway";
    }
}
