package com.example.demo.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.SiteUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookRepository bookRepository;
    private final SiteUserRepository userRepository;

    @GetMapping({"/","/booklist"})
    public String showAdminList(Model model) {
    	// ログイン中のユーザ情報を取得
        String userName = getLoginUsername();
        Long userId = userRepository.findByUsername(userName).getId();
        // ログインユーザが登録している書籍情報を取得
    	model.addAttribute("books", bookRepository.findByUserId(userId));
        return "booklist";
    }
    
    @GetMapping("/addbookform")
    public String addBook(@ModelAttribute("book") Book book) {
        return "addbookform";
    }
	@PostMapping("/process")
	public String process(Authentication loginUser, @Validated @ModelAttribute Book book, BindingResult result) {
		if(result.hasErrors()) {
			// バリデーションエラー時は登録フォームを再表示してエラー箇所にメッセージを表示。
			return "addbookform";
		}
		book.setUser(userRepository.findByUsername(loginUser.getName()));
		bookRepository.save(book);
		//登録成功時は書籍一覧にリダイレクト
		return "redirect:/booklist?complete";
	}
	
	@GetMapping("editbook/{id}")
	public String editBookInfo(@PathVariable Long id, Model model) {
        Book book = null;
        try {
        	book = bookRepository.findById(id).get();
        } catch (NoSuchElementException e) {
        	return "redirect:/booklist";
        }
        if(!isSameNameWithLoginUser(book.getUser().getUsername())) {
        	return "redirect:/booklist";
        }
		model.addAttribute("book", book);
		return "addbookform";
	}
	
	@GetMapping("/deletebook/{id}")
	public String deleteBook(@PathVariable Long id) {
		Book book = null;
        try {
        	book = bookRepository.findById(id).get();
        } catch (NoSuchElementException e) {
        	return "redirect:/booklist";
        }
        if(!isSameNameWithLoginUser(book.getUser().getUsername())) {
        	return "redirect:/booklist";
        }
        
		bookRepository.deleteById(id);
		return "redirect:/booklist?deletebook";
	}
	/**
	 * 引数で渡した文字列がログイン中のユーザ名と一致するか判別。
	 * @param name 
	 * @return 一致:true 不一致:false
	 */
	private boolean isSameNameWithLoginUser(String name) {
    	String loginUsername = getLoginUsername();
        return loginUsername.equals(name);
	}
	/**
	 * 現在ログイン中のユーザ名を取得
	 * @return ログイン中のユーザ名(SiteUser.username)
	 */
	private String getLoginUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
	}
}
