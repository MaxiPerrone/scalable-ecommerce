
package com.springcloud.ecommerce.items;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.products.service.endpoint}")
    private String endpoint;

    @Bean
    @LoadBalanced
    WebClient.Builder webClient() {
        return WebClient.builder().baseUrl(endpoint);
    }
}
