package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.front.DashboardDTO;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class InventoryController {

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession httpSession){
        String token = (String) httpSession.getAttribute("token");
        if (token == null) {
            return "login";
        }

        //TODO: backend

        model.addAttribute("dashboardDTO", new DashboardDTO());
        return "dashboard";
    }

//    @PostMapping("/create_employee")
//    public String createEmployee(@Validated @ModelAttribute )
}
