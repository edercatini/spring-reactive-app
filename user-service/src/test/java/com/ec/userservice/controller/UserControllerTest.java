package com.ec.userservice.controller;

import com.ec.userservice.model.dto.UserDto;
import com.ec.userservice.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.ec.userservice.mock.UserDtoMock.userDtoMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(UserController.class)
final class UserControllerTest {

    @MockBean
    private UserService service;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Must retrieve all users")
    void findAll() {
        List<UserDto> response = List.of(userDtoMock(), userDtoMock());
        when(service.findAll()).thenReturn(Flux.fromIterable(response));

        webTestClient.get()
                .uri("/user/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .value(result -> assertTrue(result.containsAll(response)));

        verify(service).findAll();
    }

    @Test
    @DisplayName("Must retrieve user by its id")
    void findById() {
        UserDto response = userDtoMock();
        when(service.findById(anyInt())).thenReturn(Mono.just(response));

        webTestClient.get()
                .uri("/user/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(result -> assertEquals(result, response));

        verify(service).findById(anyInt());
    }

    @Test
    @DisplayName("Must save user once")
    void save() {
        UserDto response = userDtoMock();
        when(service.save(any())).thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/user")
                .bodyValue(userDtoMock())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class)
                .value(result -> assertEquals(result, response));

        verify(service).save(any());
    }

    @Test
    @DisplayName("Must update user once")
    void update() {
        UserDto response = userDtoMock();
        when(service.update(anyInt(), any())).thenReturn(Mono.just(response));

        webTestClient.put()
                .uri("/user/{id}", 1)
                .bodyValue(userDtoMock())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(result -> assertEquals(result, response));

        verify(service).update(anyInt(), any());
    }

    @Test
    @DisplayName("Must delete user once")
    void delete() {
        when(service.delete(anyInt())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/user/{id}", 1)
                .exchange()
                .expectStatus().isNoContent();

        verify(service).delete(anyInt());
    }
}