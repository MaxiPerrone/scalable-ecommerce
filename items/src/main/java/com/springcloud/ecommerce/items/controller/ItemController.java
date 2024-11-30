package com.springcloud.ecommerce.items.controller;

import org.springframework.web.bind.annotation.RestController;

import com.springcloud.ecommerce.items.service.ItemService;
import com.springcloud.ecommerce.items.view.ItemResponse;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class ItemController {

    private final ItemService service;

    @Autowired
    private Environment environment;

    public ItemController(@Qualifier("itemServiceWebClient") ItemService service) {
        this.service = service;
    }

    @GetMapping("/configs")
    public ResponseEntity<?> configs() {
        var config = new HashMap<>();
        config.put("description", environment.getProperty("app.configuration.description"));
        return ResponseEntity.ok(config);
    }
    

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        ItemResponse item = service.findById(id);

        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(item);
    }
}
