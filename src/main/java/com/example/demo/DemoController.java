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
}
