package com.springcloud.ecommerce.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springcloud.ecommerce.users.domain.User;
import com.springcloud.ecommerce.users.repository.UserRepository;
import com.springcloud.ecommerce.users.view.UserRequest;
import com.springcloud.ecommerce.users.view.UserResponse;
import com.springcloud.ecommerce.users.view.UsersResponse;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UsersResponse list() {
        List<User> users = (List<User>) repository.findAll();

        UsersResponse response = new UsersResponse();
        response.setUsers(users.stream().map(this::response).toList());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return response(user);
        }
                    
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getByUsername(String username) {
        return repository.findByUsername(username)
            .map(user -> response(user))
            .orElse(null);
    }

    @Override
    @Transactional
    public UserResponse create(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setStatus(request.getStatus());

        var response = repository.save(user);

        return response(response);
    }

    @Override
    @Transactional
    public UserResponse update(Long id, UserRequest request) {
        User existingUser = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found by id= " + id));
        
        existingUser.setUsername(request.getUsername());
        existingUser.setPassword(passwordEncoder.encode(request.getPassword()));
        existingUser.setEmail(request.getEmail());
        existingUser.setStatus(request.getStatus());

        var response = repository.save(existingUser);
        return response(response);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private UserResponse response(User user) {
        var response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setStatus(user.getStatus().toString());

        return response;
    }
}
