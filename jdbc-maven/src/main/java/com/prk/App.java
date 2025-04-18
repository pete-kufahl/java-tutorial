package com.prk;

import com.prk.model.Book;
import com.prk.repository.BookDao;
import com.prk.repository.Dao;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Dao<Book> bookDao = new BookDao();

        List<Book> books = bookDao.findAll();

        for (Book book : books) {
            System.out.println("Id: " + book.getId());
            System.out.println("Title: " + book.getTitle());
        }
    }
}