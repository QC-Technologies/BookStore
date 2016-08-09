package com.bookstore;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by irtza on 8/7/16.
 */
@Entity
@Table(name = "book_category")
public class BookCategory {
    private @Id @GeneratedValue int id;
    private String name;
    @OneToMany(mappedBy = "bookCategory", cascade = CascadeType.ALL)
    private Set<Book> books;

    private BookCategory(){}

    public BookCategory(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
