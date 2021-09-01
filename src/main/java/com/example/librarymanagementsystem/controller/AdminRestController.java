package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.model.User;
import com.example.librarymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getalluser")
    public ModelAndView getAllUser(Pageable pageable, Model model){
        Page<User> page = userService.getAllUser(pageable);
        model.addAttribute("users",page.getContent());
        ModelAndView mav = new ModelAndView("showuser");
//        mav.addObject("users",page.getContent());
        return mav;
    }
    @PostMapping("/deluser")
    public void delUser(@RequestParam(name="id") long id, HttpServletResponse response) throws IOException {
        userService.delUserById(id);
        response.sendRedirect("/admin/getalluser");
    }


}
