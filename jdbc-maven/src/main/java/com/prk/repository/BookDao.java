package com.prk.repository;

import com.prk.model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookDao extends AbstractDao implements Dao <Book> {


    @Override
    public List<Book> findAll() {
        List<Book> books = Collections.emptyList();
        String sql = "SELECT * FROM BOOK";

        try (
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(sql);
            ) {

            books = new ArrayList<>();

            while (rset.next()) {
                Book book = new Book();
                book.setId(rset.getLong("id"));
                book.setTitle(rset.getString("title"));
                books.add(book);
            }

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return books;
    }
}