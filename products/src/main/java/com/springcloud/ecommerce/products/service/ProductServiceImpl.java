package com.springcloud.ecommerce.products.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springcloud.ecommerce.products.domain.Product;
import com.springcloud.ecommerce.products.repository.ProductRepository;
import com.springcloud.ecommerce.products.view.ProductRequest;
import com.springcloud.ecommerce.products.view.ProductResponse;
import com.springcloud.ecommerce.products.view.ProductsResponse;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductsResponse findAll() {
        List<Product> products = (List<Product>) repository.findAll();

        ProductsResponse response = new ProductsResponse();
        response.setProducts(products.stream().map(this::productResponse).toList());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return productResponse(product);
        }
                    
        return null;
    }

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        var product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCreateAt(request.getCreatedTime());

        var response = repository.save(product);
        
        return productResponse(response);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
            
    private ProductResponse productResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setCreatedTime(product.getCreateAt());
        return response;
    }
}
