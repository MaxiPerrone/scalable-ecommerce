package com.springcloud.ecommerce.items.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.springcloud.ecommerce.items.view.ItemResponse;
import com.springcloud.ecommerce.items.view.ItemsResponse;
import com.springcloud.ecommerce.items.view.Product;
import com.springcloud.ecommerce.items.view.Products;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Service
public class ItemServiceWebClient implements ItemService {

    private final WebClient.Builder client;

    public ItemServiceWebClient(Builder client) {
        this.client = client;
    }

    @Override
    public ItemsResponse findAll() {
        Products productsResponse = client.build()
            .get()
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Products.class)
            .block();

        ItemsResponse response = new ItemsResponse();
        response.setItems(productsResponse.getProducts().stream().map(this::itemResponse).toList());
        return response;
    }

    @Override
    public ItemResponse findById(Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        Product product = client.build()
            .get()
            .uri("/{id}", params)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(Product.class)
            .block();

            return itemResponse(product);
    }

    private ItemResponse itemResponse(Product product) {
        return new ItemResponse(product, new Random().nextInt(10) + 1);
    }
}
