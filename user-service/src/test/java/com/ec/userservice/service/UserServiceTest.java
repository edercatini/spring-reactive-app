package com.ec.userservice.service;

import com.ec.userservice.exception.UserNotFoundException;
import com.ec.userservice.model.dto.UserDto;
import com.ec.userservice.model.entity.User;
import com.ec.userservice.model.mapper.UserMapper;
import com.ec.userservice.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserMapper mapper;

    @Mock
    private UserRepository repository;

    @Test
    @DisplayName("Must find all users")
    void findAll() {
        when(repository.findAll()).thenReturn(Flux.just(mock(User.class)));
        when(mapper.toDto(any(User.class))).thenReturn(mock(UserDto.class));

        StepVerifier.create(service.findAll())
                .expectNextCount(1L)
                .verifyComplete();

        verify(repository).findAll();
        verify(mapper).toDto(any(User.class));
    }

    @Test
    @DisplayName("Must find user by id")
    void findById() {
        when(repository.findById(anyInt())).thenReturn(Mono.just(mock(User.class)));
        when(mapper.toDto(any(User.class))).thenReturn(mock(UserDto.class));

        StepVerifier.create(service.findById(1))
                .expectNextCount(1L)
                .verifyComplete();

        verify(repository).findById(anyInt());
        verify(mapper).toDto(any(User.class));
    }

    @Test
    @DisplayName("Must not find user by id")
    void mustNotFindById() {
        when(repository.findById(anyInt())).thenReturn(Mono.empty());

        StepVerifier.create(service.findById(1))
                .expectError(UserNotFoundException.class);

        verify(repository).findById(anyInt());
        verify(mapper, times(0)).toDto(any(User.class));
    }

    @Test
    @DisplayName("Must save user once")
    void save() {
        when(repository.save(any(User.class))).thenReturn(Mono.just(mock(User.class)));
        when(mapper.toEntity(any(UserDto.class))).thenReturn(mock(User.class));
        when(mapper.toDto(any(User.class))).thenReturn(mock(UserDto.class));

        StepVerifier.create(service.save(Mono.just(mock(UserDto.class))))
                .expectNextCount(1L)
                .verifyComplete();

        verify(repository).save(any(User.class));
        verify(mapper).toEntity(any(UserDto.class));
        verify(mapper).toDto(any(User.class));
    }

    @Test
    @DisplayName("Must update user once")
    void update() {
        when(repository.findById(anyInt())).thenReturn(Mono.just(mock(User.class)));
        when(repository.save(any(User.class))).thenReturn(Mono.just(mock(User.class)));
        when(mapper.toEntity(any(UserDto.class))).thenReturn(mock(User.class));
        when(mapper.toDto(any(User.class))).thenReturn(mock(UserDto.class));

        StepVerifier.create(service.update(1, Mono.just(mock(UserDto.class))))
                .expectNextCount(1L)
                .verifyComplete();

        verify(repository).findById(anyInt());
        verify(repository).save(any(User.class));
        verify(mapper).toEntity(any(UserDto.class));
        verify(mapper).toDto(any(User.class));
    }

    @Test
    @DisplayName("Must handle update user exception")
    void updateException() {
        when(repository.findById(anyInt())).thenReturn(Mono.empty());

        StepVerifier.create(service.update(1, Mono.just(mock(UserDto.class))))
                .expectError(UserNotFoundException.class);

        verify(repository).findById(anyInt());
        verify(repository, times(0)).save(any(User.class));
        verify(mapper, times(0)).toEntity(any(UserDto.class));
        verify(mapper, times(0)).toDto(any(User.class));
    }

    @Test
    @DisplayName("Must delete user once")
    void delete() {
        when(repository.findById(anyInt())).thenReturn(Mono.just(mock(User.class)));
        when(repository.delete(any(User.class))).thenReturn(Mono.empty());

        StepVerifier.create(service.delete(1)).verifyComplete();

        verify(repository).findById(anyInt());
        verify(repository).delete(any(User.class));
    }

    @Test
    @DisplayName("Must handle delete exception")
    void deleteException() {
        when(repository.findById(anyInt())).thenReturn(Mono.empty());

        StepVerifier.create(service.delete(1)).expectError(UserNotFoundException.class);

        verify(repository).findById(anyInt());
        verify(repository, times(0)).delete(any(User.class));
    }
}