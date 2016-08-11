package com.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by irtza on 8/9/16.
 */
@org.springframework.web.bind.annotation.RestController
public class CustomRestController {

    private BookRepository repository;

    @Autowired
    public CustomRestController(BookRepository repository){
        this.repository = repository;
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

    /**
     * Returns top 10 sold books
     * @return string
     */

    @RequestMapping(value = "get_top_sellers", method = RequestMethod.GET)
    public String getTopSellers(){
        Pageable topTen = new PageRequest(0,9);
        List<Book> list = repository.topSellers(topTen);
        return convertToString(list);
    }


}
