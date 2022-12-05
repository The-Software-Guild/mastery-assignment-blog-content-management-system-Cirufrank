/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Author;
import com.we.blogcms.model.Body;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 *
 * @author ciruf
 */
public class AuthorParameterResolver implements ParameterResolver {
    final Author testAuthor = new Author();
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().getType() == Author.class;
    }
    
    @Override
    public Author resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        testAuthor.setFirstName("Test first name");
        testAuthor.setLastName("Test last name");
        testAuthor.setEmail("Test email");
        testAuthor.setPassword("Test hashed password");
        testAuthor.setDisplayName("Test display name");
        return testAuthor;
    }
}
