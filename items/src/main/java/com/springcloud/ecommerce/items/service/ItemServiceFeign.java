
package com.springcloud.ecommerce.items.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springcloud.ecommerce.items.client.ProductFeignClient;
import com.springcloud.ecommerce.items.view.ItemRequest;
import com.springcloud.ecommerce.items.view.ItemResponse;
import com.springcloud.ecommerce.items.view.ItemsResponse;
import com.springcloud.ecommerce.items.view.Product;
import com.springcloud.ecommerce.items.view.Products;

import feign.FeignException.FeignClientException;

@Service
public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductFeignClient productAPIClient;

    @Override
    public ItemsResponse findAll() {
        Products productsResponse = productAPIClient.list();
        
        ItemsResponse response = new ItemsResponse();
        response.setItems(productsResponse.getProducts().stream().map(this::itemResponse).toList());
        return response;
    }

    @Override
    public ItemResponse findById(Long id) {
        try {
            Product product = productAPIClient.get(id);
            return itemResponse(product);
        } catch(FeignClientException e) {
            return null;
        }
    }

    @Override
    public ItemResponse create(ItemRequest request) {
        Product product = productAPIClient.create(request);
        return itemResponse(product);
    }

    @Override
    public void delete(long id) {
        productAPIClient.delete(id);
    }

    private ItemResponse itemResponse(Product product) {
        return new ItemResponse(product, new Random().nextInt(10) + 1);
    }
}
