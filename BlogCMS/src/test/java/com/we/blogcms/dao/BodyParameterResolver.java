/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Body;
import com.we.blogcms.model.Tag;
import java.util.List;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 *
 * @author ciruf
 */
public class BodyParameterResolver implements ParameterResolver {
    final Body testBody = new Body();
    
    
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().getType() == Body.class;
    }
    
    @Override
    public Body resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        testBody.setBody("This is a test");
        return testBody;
    }
}
