package com.ec.userservice.mock;

import com.ec.userservice.model.dto.UserDto;

public final class UserDtoMock {

    public static UserDto userDtoMock() {
        return UserDto.builder()
                .id(1)
                .name("Foo")
                .balance(300)
                .build();
    }
}