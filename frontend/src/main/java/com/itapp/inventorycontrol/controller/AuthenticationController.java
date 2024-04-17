package com.itapp.inventorycontrol.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
public class AuthenticationController {
    @GetMapping("/")
    public String test(Model model, HttpSession session) {
        String a = (String) session.getAttribute("test");
        if (a != null) {
            model.addAttribute("testAttr", a);
        } else {
            model.addAttribute("testAttr", "no session");
        }
        return "test";
    }

    @PostMapping("/test")
    public String postTest(HttpServletRequest request) {
        String a = (String) request.getSession().getAttribute("test");
        if (a == null) {
            request.getSession().setAttribute("test", "aaa");
        }
        return "redirect:/";
    }
}
