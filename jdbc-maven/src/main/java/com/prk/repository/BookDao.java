package com.prk.repository;

import com.prk.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Book> findById(long id) {
        Optional<Book> book = Optional.empty();
        String sql = "SELECT ID, TITLE FROM BOOK WHERE ID = ?";

        try (
                Connection con = getConnection();
                PreparedStatement prepStmt = con.prepareStatement(sql);
        ) {
            prepStmt.setLong(1, id);

            try (ResultSet rset = prepStmt.executeQuery();) {
                Book resBook = new Book();

                if(rset.next()) {
                    resBook.setId(rset.getLong("ID"));
                    resBook.setTitle(rset.getString("TITLE"));
                }
                book = Optional.of(resBook);
            }
        }
        catch (SQLException sqe) { sqe.printStackTrace();}

        return book;
    }
}