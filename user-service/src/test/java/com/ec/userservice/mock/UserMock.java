package com.ec.userservice.mock;

import com.ec.userservice.model.entity.User;

public final class UserMock {

    public static User userMock() {
        return User.builder()
                .id(1)
                .name("Foo")
                .balance(300)
                .build();
    }
}