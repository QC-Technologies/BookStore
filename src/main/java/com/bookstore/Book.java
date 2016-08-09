package com.bookstore;
//3rd party import
import lombok.Data;
//Builtin import
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

/**
 * Created by kamal on 8/5/16.
 */
@Data
@Entity
public class Book {
    private @Id @GeneratedValue Long id;
    private String title;
    private  String author;
    private double price;
    private String description;
    private String image;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToOne
    @JoinColumn(name = "book_category_id")
    private BookCategory bookCategory;

    private Book(){}

    /**
     * @param title: Book Title
     * @param author: Book Author
     * @param price: Bok Price
     * @param description: Book Description
     * @param image: Book image path
     * @param quantity: Book Quantity
     * @param bookCategory: Book Category
     */
    public Book(String title, String author, double price, String description, String image,int quantity, BookCategory bookCategory) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.bookCategory = bookCategory;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public BookCategory getBookCategory(){
        return this.bookCategory;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public Long getId() {

        return id;
    }
}
