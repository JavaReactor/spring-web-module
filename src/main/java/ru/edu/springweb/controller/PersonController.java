package ru.edu.springweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonController {

    @GetMapping("/")
    public String authorPage(Model model) {
        model.addAttribute("author", "Виталий Горяев");
        return "author";
    }

    @GetMapping("/hobby")
    public String hobbyPage(Model model) {
        model.addAttribute("hobby", "путешествия, плавание, фотография");
        return "hobby";
    }
}
