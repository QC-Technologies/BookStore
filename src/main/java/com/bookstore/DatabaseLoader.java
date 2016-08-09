package com.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by irtza on 8/5/16.
 *
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

    private final BookRepository repository;
    private final BookCategoryRepository bookCategoryRepository;

    @Autowired
    public DatabaseLoader(BookRepository repository, BookCategoryRepository bookCategoryRepository) {
        this.repository = repository;
        this.bookCategoryRepository = bookCategoryRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        BookCategory fiction = new BookCategory("Fiction");
        BookCategory mystery = new BookCategory("Mystery");
        this.bookCategoryRepository.save(fiction);
        this.bookCategoryRepository.save(mystery);
        this.repository.save(new Book("The Ice Berg", "Anonymous",5.00,
                "The novel is about magic","images/book.jpg", 100
                ,fiction));
        this.repository.save(new Book("Mr Mercedes", "Stephen King",12.00,
                "The novel is about a wear wolf","images/book3.gif",
                10, mystery));
        this.repository.save(new Book("George Orwell", "Anonymous",10.00,
                "The novel is about a wear wolf","images/book2.jpeg",
                10, mystery));
        this.repository.save(new Book("The Maker of Swans", "Paraic O'Donnell",10.00,
                "The novel is about a wear wolf","images/swan.jpg",
                10, mystery));
        this.repository.save(new Book("Pieces of Life", "Charles Fernyhough", 30.00,
                "The novel is about a wear wolf","images/original.jpg",
                10, mystery));
        this.repository.save(new Book("The Road", "Cormac McCarthy", 20.00,
                "The novel is about a wear wolf","images/the-road.jpg",
                12, mystery));
    }
}
