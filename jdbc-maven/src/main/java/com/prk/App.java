package com.prk;

import com.prk.model.Book;
import com.prk.repository.BookDao;
import com.prk.repository.Dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {
        boolean ifCreate = false;
        boolean ifUpdate = false;
        int batchUpdate = 0;

        Dao<Book> bookDao = new BookDao();
        List<Book> books = bookDao.findAll();

        for (Book book : books) {
            System.out.println("Id: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Rating: " + book.getRating());
        }

        Optional<Book> optBook = bookDao.findById(1);
        if(optBook.isPresent()) {
            Book book = optBook.get();
            System.out.println("Id: " + book.getId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Rating: " + book.getRating());

            if (ifUpdate) {
                book.setTitle("Effective Java: Second Edition");
                bookDao.update(book);
            }
        }

        if (ifCreate) {
            Book newBook = new Book();
            newBook.setTitle("The River Why");
            newBook = bookDao.create(newBook);

            System.out.println("Id: " + newBook.getId());
            System.out.println("Title: " + newBook.getTitle());
        }

        if (batchUpdate > 0) {
            books = bookDao.findAll();
            List<Book> updatedEntries = books.stream()
                            .peek(b -> b.setRating(batchUpdate))
                            .collect(Collectors.toList());
            var ret = bookDao.update(updatedEntries);
            System.out.println("updated ids: " + ret.toString());
        }
    }
}