/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.TestApplicationConfiguration;
import com.we.blogcms.model.Body;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author ciruf
 */
@ExtendWith(BodyParameterResolver.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class BodyDaoDBTest {
    @Autowired
    BodyDao bodyDao;
    
    public BodyDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        final List<Body> postBodies = bodyDao.getAllPostBodies();
        for (Body postBody : postBodies) {
            bodyDao.deleteBodyById(postBody.getBodyId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addBody getBodyById methods, of class BodyDaoDB.
     */
    @Test
    public void testAddGetBody(Body body) {
        final Body bodyAdded = bodyDao.addBody(body);
        final Body bodyRetrieved = bodyDao.getBodyById(bodyAdded.getBodyId());
        assertEquals(body.getBody(), bodyAdded.getBody());
        assertEquals(bodyAdded, bodyRetrieved);
    }

    /**
     * Test of updateBody method, of class BodyDaoDB.
     */
    @Test
    public void testUpdateBody(Body body) {
        final Body bodyAdded = bodyDao.addBody(body);
        final String oldBody = bodyAdded.getBody();
        body.setBody("This an updated body");
        final Body bodyUpdated = bodyDao.updateBody(body);
        assertNotEquals(oldBody, bodyUpdated.getBody());
    }

    /**
     * Test of deleteBodyById method, of class BodyDaoDB.
     */
    @Test
    public void testDeleteBodyById(Body body) {
        final Body bodyAdded = bodyDao.addBody(body);
        bodyDao.deleteBodyById(bodyAdded.getBodyId());
        final Body bodyRetrieved = bodyDao.getBodyById(bodyAdded.getBodyId());
        assertNull(bodyRetrieved);
    }
    
}
