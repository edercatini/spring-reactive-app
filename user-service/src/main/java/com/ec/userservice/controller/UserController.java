package com.ec.userservice.controller;

import com.ec.userservice.model.dto.UserDto;
import com.ec.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping(value = "all", produces = MediaType.APPLICATION_NDJSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<UserDto> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserDto> save(@RequestBody Mono<UserDto> dto) {
        return service.save(dto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserDto> update(@PathVariable Integer id, @RequestBody Mono<UserDto> dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}