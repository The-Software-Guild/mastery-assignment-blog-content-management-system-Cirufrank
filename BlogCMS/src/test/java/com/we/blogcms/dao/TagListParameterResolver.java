/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Status;
import com.we.blogcms.model.Tag;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 *
 * @author ciruf
 */
public class TagListParameterResolver implements ParameterResolver {
    final List<Tag> testTags = new ArrayList<>();
    final Tag tag1 = new Tag();
    final Tag tag2 = new Tag();
    
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().getType() == List.class;
    }
    
    @Override
    public List<Tag> resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        tag1.setTag("This-is-a-test-tag-1");
        tag2.setTag("This-is-a-test-tag-2");
        tag1.setStatus(Status.active);
        tag2.setStatus(Status.active);
        testTags.add(tag1);
        testTags.add(tag2);
        return testTags;
    }
}
