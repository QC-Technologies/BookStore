package com.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by irtza on 8/5/16.
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
        this.repository.save(new Book("Harry Potter", "JK",10.00,"The novel is about magic","images/book.jpg", 10
                ,fiction));
        this.repository.save(new Book("Be o Wolf", "KK Modi",10.00,"The novel is about a wear wolf","images/book3.gif",
                10, mystery));
    }
}
