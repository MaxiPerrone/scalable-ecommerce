package com.springcloud.ecommerce.users.view;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsersResponse {

    @JsonProperty("users")
    private List<UserResponse> users;

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }
}
