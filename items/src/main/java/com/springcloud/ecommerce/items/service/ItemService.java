package com.springcloud.ecommerce.items.service;

import org.springframework.web.bind.annotation.RequestBody;

import com.springcloud.ecommerce.items.view.ItemRequest;
import com.springcloud.ecommerce.items.view.ItemResponse;
import com.springcloud.ecommerce.items.view.ItemsResponse;

public interface ItemService {

    ItemsResponse findAll();

    ItemResponse findById(Long id);

    ItemResponse create(@RequestBody ItemRequest request);

    void delete(long id);
}
