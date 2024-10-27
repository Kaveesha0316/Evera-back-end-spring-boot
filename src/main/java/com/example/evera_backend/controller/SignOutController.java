package com.example.evera_backend.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/SignOut")
public class SignOutController {

    @GetMapping
    public void signOut(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect("signin.html");
    }
}
