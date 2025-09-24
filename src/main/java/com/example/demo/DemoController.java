// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
   public DemoController() {
   }

   @GetMapping({"/hello"})
   public String hello(Model model) {
      model.addAttribute("data", " 방갑습니다.");
      return "hello";
   }

   @GetMapping({"/about_detailed"})
   public String about() {
      return "about_detailed";
   }

   
@GetMapping("/test1")
public String thymeleaf_test1(Model model) {
model.addAttribute("data1", "<h2> 방갑습니다 </h2>");
model.addAttribute("data2", "태그의 속성 값");
model.addAttribute("link", 01);
model.addAttribute("name", "홍길동");
model.addAttribute("para1", "001");
model.addAttribute("para2", 002);
return "thymeleaf_test1";
}
}
