package com.prk.repository;

import com.prk.model.Book;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> findAll();

    Optional<Book> findById(long id);
}