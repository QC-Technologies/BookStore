package com.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by irtza on 8/8/16.
 */
@RestController
public class CartController {
    private BookRepository repository;

    @Autowired
    public CartController(BookRepository repository){
        this.repository = repository;
    }

    /**
     * Decreases quantity of book in Database
     * @param book: Book Object
     * @return ResponseEntity
     */
    @RequestMapping(value = "add_to_cart", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Book> update(@RequestBody Book book) {
        Book bookToUpdate = repository.findByTitle(book.getTitle());
        if(bookToUpdate.getQuantity() > 0){
            bookToUpdate.setQuantity(bookToUpdate.getQuantity()-1);
            repository.save(bookToUpdate);
            return new ResponseEntity<Book>(bookToUpdate, HttpStatus.OK);
        }
        return null;
    }

    /**
     * Adds quantity of the books removed from cart to Database
     * @param book: Book object
     * @return true|false
     */
    @RequestMapping(value = "remove_from_cart", method = RequestMethod.POST, consumes = "application/json")
    public boolean removeFromCart(@RequestBody Book book) {
        Book bookToUpdate = repository.findByTitle(book.getTitle());
        if(bookToUpdate != null){
            bookToUpdate.setQuantity(bookToUpdate.getQuantity()+book.getQuantity());
            repository.save(bookToUpdate);
            return true;
        }
        return false;
    }

    /**
     * Finds id of book against it's title and returns it
     * @param title: Book title
     * @return Long|null
     */
    @RequestMapping(value = "get_id", method = RequestMethod.GET)
    public Long getId(@RequestParam(name = "title", defaultValue = "") String title){
        Book foundBook = repository.findByTitle(title);
        if(foundBook != null){
        return foundBook.getId();
        }
        return null;
    }

    /**
     *Converts list of books into a JSON parsable string
     * @param list: List<Book>
     * @return String
     */
    public String convertToString(List<Book> list){
        String str = "[";
        for(int i=0; i< list.size(); i++){
            Book tempBook = list.get(i);
             str =str+ "{\"title\":"+"\""+tempBook.getTitle()+"\""+ ", \"author\":"+"\""+tempBook.getAuthor()+"\""+
                     " ,\"id\":"+tempBook.getId()+
                     " ,\"price\":"+tempBook.getPrice()+" ,\"description\":"+"\""+tempBook.getDescription()+"\""+
                     " ,\"image\":"+"\""+tempBook.getImage()+"\""+"}";
            if(i<list.size()-1){
                str=str+",";
            }
        }
        return str = str+"]";
    }

    /**
     *Searches books against book title
     * @param title: Book title
     * @return String
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(@RequestParam(name = "title") String title){
        List<Book> list = repository.findAllByTitle('%'+title.toLowerCase()+'%');
        return convertToString(list);

    }
}