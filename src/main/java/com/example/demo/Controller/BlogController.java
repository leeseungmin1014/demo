// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.service.BlogService; // 최상단 서비스 클래스 연동 추가
import com.example.demo.domain.Article;

@Controller
public class BlogController {
    @Autowired
    BlogService blogService; // DemoController 클래스 아래 객체 생성

    @GetMapping("/Article_list") // 게시판 링크 지정
    public String article_list(Model model) {
        List<Article> list = blogService.findAll(); // 게시판 리스트
        model.addAttribute("articles", list); // 모델에 추가
        return "article_list"; // .HTML 연결
    }

}
