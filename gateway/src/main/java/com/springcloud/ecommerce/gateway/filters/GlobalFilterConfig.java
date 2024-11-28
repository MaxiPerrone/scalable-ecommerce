package com.springcloud.ecommerce.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalFilterConfig implements GlobalFilter {

    private final Logger logger = LoggerFactory.getLogger(GlobalFilterConfig.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("filter before request");

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.info("filter after request");
        }));
    }

}
