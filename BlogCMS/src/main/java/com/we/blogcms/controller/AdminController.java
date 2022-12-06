/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ciruf
 * This controller is for both marketers and managers being able 
 * to create and update their own blogs
 * 
 * Marketers can only see and edit their own blogs from here, and managers
 * can see all posts and approve posts
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @GetMapping
    public String getManagerBlogsPage(Model model) {
        //For marketing only add their own blogs to the controller
        //For maanagers add everyone's blogs to the controller
        return "adminHome";
    }
    
    @PostMapping
    public String searchForAdminBlogsWithTags() {
        String requestUrl = "redirect:/adminHome";
        return requestUrl;
    }
    
    @GetMapping("/signup")
    public String getCreateAccountPage() {
        return "addAccount";
    }
    
    @PostMapping("/signup")
    public String creatAccount() {
        return "redirect:/adminHome";
    }
    
    @GetMapping("/login")
    public String getAdminLoginPage() {
        return "adminLogin";
    }
    
    @PostMapping("/login")
    public String loginAdmin() {
        return "redirect:/adminHome";
    }
    
    @GetMapping("/add-blog")
    public String getAddBlogPage() {
        return "addBlog";
    }
    
    @PostMapping("/add-blog")
    public String addBlog() {
        return "redirect:/adminHome";
    }
    
    @GetMapping("/edit-blog")
    public String getEditBlogPage() {
        return "editBlog";
    }
    
    @PostMapping("/edit-blog")
    public String editBlog() {
        return "redirect:/adminHome";
    }
    
    @GetMapping("/delete-blog")
    public String deleteBlogById(int id) {
        return "redirect:/admin-home";
    }
 
}
