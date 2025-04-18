package com.prk.repository;

import com.prk.model.Book;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> findAll();

    Optional<Book> findById(long id);

    T create(T t);

    T update(T t);

    int[] update(List<T> t);

    int delete(T t);
}