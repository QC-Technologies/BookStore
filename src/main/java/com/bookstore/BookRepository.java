package com.bookstore;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by irtza on 8/5/16.
 */
public interface BookRepository extends CrudRepository<Book, Long> {
    /**
     * Return book object against title
     * @param title: Book title
     * @return Book
     */
    Book findByTitle(@Param("title") String title);

    /**
     *Returns list of books against title i.e: wildcard search
     * @param title: Book title
     * @return List<Book>
     */
    @Query("select DISTINCT x from Book x where LCASE(title) LIKE ?1")
    java.util.List<Book> findAllByTitle(@Param("") String title);

    @Query("select x from Book x order by x.sold desc")
    List<Book> topSellers(Pageable pageable);
}