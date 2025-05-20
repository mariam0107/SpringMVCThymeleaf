package com.springmvcapp.spring_mvc_thymeleaf.controller;

import com.springmvcapp.spring_mvc_thymeleaf.model.BookModel;
import com.springmvcapp.spring_mvc_thymeleaf.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
//@RequiredArgsConstructor
public class BookController {
    private final BookService bookservice;

    public BookController(BookService bookservice) {
        this.bookservice = bookservice;
    }
    @GetMapping
        public String getBookPage(@RequestParam(required = false, name = "login") String login,
                                  @RequestParam(required = false) String email , Model model, HttpServletRequest request) {
        HttpSession session=request.getSession();
        if(login !=null && !login.isEmpty()){
            session.setAttribute("USER_LOGIN",login);
        }
        String userLogin = (String)session.getAttribute("USER_LOGIN");
        model.addAttribute("USER_LOGIN",userLogin);
        List<BookModel> books = bookservice.getAllBooksByLogin(login);
        model.addAttribute("userBooks",books);

        return "book_page";
    }
    @GetMapping("/create")
    public String getCreateBookPage(Model model){
        model.addAttribute("newBook",new BookModel());
        return "create_book_page";
    }
    @PostMapping("/createBook")
    public String createBook(@ModelAttribute BookModel book){
        bookservice.save(book);
        return "redirect:/books";
    }
    @GetMapping("/edit/{title}")
    public String getEditBookPage(Model model,@PathVariable String title){
        BookModel byTitle = bookservice.findByTitleAndDelete(title);
        model.addAttribute("bookToEdit",byTitle);
        return "edit_book_page";
    }
    @PostMapping("/editBook")
    public String editBook(@ModelAttribute BookModel book){
        bookservice.edit(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{title}")
    public String delete(@PathVariable String title){
        bookservice.delete(title);
        return "redirect:/books";
    }
}
