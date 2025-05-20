package com.springmvcapp.spring_mvc_thymeleaf.service;

import com.springmvcapp.spring_mvc_thymeleaf.model.BookModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private static List<BookModel> books;
    static{
        books = new ArrayList<>();
        books.add(new BookModel("The Great Gatsby",1925));
        books.add(new BookModel("The Great Books1",1961));
        books.add(new BookModel("The Great Book2",1972));
        books.add(new BookModel("The Not very Great Books",1989));
    }
    public List<BookModel> getAllBooksByLogin(String login){
        if (login != null && !login.trim().isEmpty()) {
            return books;
        }
        return books.stream()
                .filter(book -> book.getYear() == 1925)
                .toList();
    }

    public void save(BookModel book){
        books.add(book);
    }

    public BookModel findByTitleAndDelete(String title){
      BookModel bookModel = books.stream()
                .filter(it->it.getTitle().equals(title))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
       books.remove(bookModel);
       return bookModel;
    }
    public void edit(BookModel book){
        save(book);
    }
    public void delete(String title){
        findByTitleAndDelete(title);
    }
}
