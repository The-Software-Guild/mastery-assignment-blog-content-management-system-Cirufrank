/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.controller;

import com.we.blogcms.dao.PostDao;
import com.we.blogcms.model.Post;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ciruf
 */
@Controller
@RequestMapping("/")
public class HomeController {
    final static int LATEST_POSTS_TO_PREVIEW = 5;
    
//    @Autowired
//    PostDao postDao;
    
    @GetMapping
    public String getHomePage(Model model) {
//        final List<Post> posts = postDao.getLatestShowablePosts(LATEST_POSTS_TO_PREVIEW);
//        model.addAttribute("posts", posts);
        return "index";
    }
    
}
