/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.TestApplicationConfiguration;
import com.we.blogcms.model.Post;
import com.we.blogcms.model.Status;
import com.we.blogcms.model.Tag;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author ciruf
 */

public class PostListParameterResolver implements ParameterResolver {
    
    final List<Post> postList = new ArrayList<>();
    final Post post1 = new Post();
    final Post post2 = new Post();
    final Post post3 = new Post();
    final Post post4 = new Post();
    
    
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().getType() == List.class;
    }
    
    @Override
    public List<Post> resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        postList.add(post1);
        postList.add(post2);
        postList.add(post3);
        postList.add(post4);
        post1.setTitle("This is a test title 1");
        post2.setTitle("This is a test title 2");
        post3.setTitle("This is a test title  3");
        post4.setTitle("This is a test title  4");
        
        post1.setHeadline("This is a test headline 1, activation date and not expired");
        post2.setHeadline("This is a test headline 2 not expired not yet activation date");
        post3.setHeadline("This is a test headline 3 null activation null expiration");
        post4.setHeadline("This is a test headline 3  activation date reached but is expired");
        
        
        
        post1.setActivationDate(LocalDateTime.now().minusMinutes(2));
        post1.setExpirationDate(LocalDateTime.now().plusMonths(2));
        post2.setActivationDate(LocalDateTime.now().plusMonths(2));
        post2.setExpirationDate(LocalDateTime.now().plusMonths(3));
        post4.setActivationDate(LocalDateTime.now());
        post4.setExpirationDate(LocalDateTime.now());
        
        post1.setStatus(Status.deleted);
        post2.setStatus(Status.active);
        post3.setStatus(Status.inactive);
        post4.setStatus(Status.pending);
        
        return postList;
    }
}
