package com.kata.bananaexport.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InMemoryRepository <ID, T>{

    default String nextKeyValue(){
        return UUID.randomUUID().toString();
    }
     Optional<T> findById(final ID id);

     List<T> findAll();

     T delete(final T entity);

     T update(final T entity);

    T save(T recipient);
}
