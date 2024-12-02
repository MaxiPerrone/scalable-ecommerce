package com.springcloud.ecommerce.users.service;

import com.springcloud.ecommerce.users.view.UserRequest;
import com.springcloud.ecommerce.users.view.UserResponse;
import com.springcloud.ecommerce.users.view.UsersResponse;

public interface UserService {

    UsersResponse list();

    UserResponse getById(Long id);

    UserResponse getByUsername(String username);

    UserResponse create(UserRequest request);

    UserResponse update(Long id, UserRequest request);

    void delete(Long id);
}
