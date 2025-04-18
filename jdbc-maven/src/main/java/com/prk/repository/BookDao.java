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
                book.setRating(rset.getInt("rating"));
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
        String sql = "SELECT ID, TITLE, RATING FROM BOOK WHERE ID = ?";
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
                    resBook.setRating(rset.getInt("RATING"));
                }
                book = Optional.of(resBook);
            }
        }
        catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return book;
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO BOOK (TITLE) VALUES (?)";
        try (
            Connection con = getConnection();
            PreparedStatement prepStmt = con.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
        ) {
            prepStmt.setString(1, book.getTitle());
            prepStmt.executeUpdate();
            try (ResultSet genKeys = prepStmt.getGeneratedKeys()) {
                if(genKeys.next()) {
                    book.setId(genKeys.getLong(1));
                }
            }
        }
        catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE BOOK SET TITLE = ? WHERE ID = ?";
        try (
            Connection con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setLong(2, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return book;
    }

    @Override
    public int[] update(List<Book> books) {
        int[] records = {};
        String sql = "UPDATE BOOK SET TITLE = ?, RATING = ? WHERE ID = ?";
        try (
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
        ) {
            for (Book book : books) {
                stmt.setString(1, book.getTitle());
                stmt.setInt(2, book.getRating());
                stmt.setLong(3, book.getId());
                stmt.addBatch();
            }
            records = stmt.executeBatch();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
        return records;
    }
}