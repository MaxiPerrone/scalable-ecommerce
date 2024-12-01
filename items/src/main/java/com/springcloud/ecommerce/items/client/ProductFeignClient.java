package com.springcloud.ecommerce.items.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.springcloud.ecommerce.items.view.ItemRequest;
import com.springcloud.ecommerce.items.view.Product;
import com.springcloud.ecommerce.items.view.Products;

@FeignClient(name="products")
public interface ProductFeignClient {

    @GetMapping("/products")
    public Products list();

    @GetMapping("/products/{id}")
    public Product get(@PathVariable Long id);

    @PostMapping("/products")
    public Product create(ItemRequest request);

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Long id);
}
