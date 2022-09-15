package com.ec.userservice.service;

import com.ec.userservice.exception.UserNotFoundException;
import com.ec.userservice.model.dto.UserDto;
import com.ec.userservice.model.mapper.UserMapper;
import com.ec.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    public Flux<UserDto> findAll() {
        return repository.findAll()
                .map(mapper::toDto);
    }

    public Mono<UserDto> findById(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .map(mapper::toDto);
    }

    public Mono<UserDto> save(Mono<UserDto> dto) {
        return dto.map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    public Mono<UserDto> update(Integer id, Mono<UserDto> dto) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .flatMap(user -> dto.map(mapper::toEntity).doOnNext(data -> data.setId(id)))
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    public Mono<Void> delete(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException()))
                .flatMap(repository::delete);
    }
}