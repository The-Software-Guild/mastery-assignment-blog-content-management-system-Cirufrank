/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.controller;

import com.we.blogcms.dao.PostDao;
import com.we.blogcms.dao.TagDao;
import com.we.blogcms.model.Post;
import com.we.blogcms.model.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ciruf
 * 
 * This controller is for the public being able to view blogs, filter
 * for blogs with tags, and look at specific blogs
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
//    @Autowired
//    PostDao postDao;
//    
//    @Autowired
//    TagDao tagDao;
    
    @GetMapping
    public String getBlogLandingPage(@RequestParam(required = false) List<String> tagIds, Model model) {
//        List<Post> posts;
//        if (tagIds != null) {
//            final List<Tag> searchedTags = new ArrayList<>();
//            for (String tagId: tagIds) {
//                final Tag postTag = tagDao.getTagById(Integer.parseInt(tagId));
//                searchedTags.add(postTag);
//            }
//            
//            posts = postDao.getShowablePostsByTags(searchedTags);
//        } else {
//            posts = postDao.getShowablePosts();
//        }
//        
//        model.addAttribute("posts", posts);
        return "blogHome";
    }

    @PostMapping
    public String makeBlogSearch(HttpServletRequest request) {
        final String[] tagIds = request.getParameterValues("tagIds");
        String requestUrl = "redirect:/blog";
        for (int index = 0; index < tagIds.length; index += 1) {
            final String currentTagId = tagIds[index], DELIMITER = ",";
            final int FIRST_INDEX = 0;
            final int LAST_INDEX = tagIds.length - 1;
            if (index == FIRST_INDEX) {
                requestUrl += "?tagIds=" + currentTagId + DELIMITER;
                continue;
            } 
            if (index == LAST_INDEX) {
                requestUrl += currentTagId;
                break;
            }
            requestUrl += currentTagId + DELIMITER;
        }
        return requestUrl;
    }
    
    @GetMapping("/{id}")
    public String getBlogDetailPage(@PathVariable int id, Model model) {
//        final Post post = postDao.getPostById(id);
//        model.addAttribute("post", post);
        return "blogDetail";
    }
    
    
}
