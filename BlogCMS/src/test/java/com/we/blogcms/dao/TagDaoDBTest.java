/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.TestApplicationConfiguration;
import com.we.blogcms.model.Status;
import com.we.blogcms.model.Tag;
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
@ExtendWith(TagListParameterResolver.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class TagDaoDBTest {
    final static int FIRST_TAG_INDEX = 0, 
            SECOND_TAG_INDEX = 1;
    
    @Autowired
    TagDao tagDao;
    
    public TagDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        final List<Tag> tags = tagDao.getAllTags();
        for (Tag tag: tags) {
            tagDao.deleteTagById(tag.getTagId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addTag and getTagById methods, of class TagDaoDB.
     */
    @Test
    public void testaddGetTag(List<Tag> tags) {
        final Tag tagToAdd = tags.get(FIRST_TAG_INDEX);
        final Tag tagAdded = tagDao.addTag(tagToAdd);
        final Tag tagRetrieved = tagDao.getTagById(tagAdded.getTagId());
        assertEquals(tagAdded, tagRetrieved);
        assertEquals(tagToAdd.getTag(), tagAdded.getTag());
    }

    /**
     * Test of getAllTags method, of class TagDaoDB.
     */
    @Test
    public void testGetAllTags(List<Tag> tags) {
        for (Tag tag: tags) {
            tagDao.addTag(tag);
        }
        final List<Tag> allTags = tagDao.getAllTags();
        assertEquals(tags.size(), allTags.size());
        for (int index = 0; index < tags.size(); index += 1) {
            final Tag actualTag = tags.get(index);
            final Tag testTag = allTags.get(index);
            assertEquals(actualTag.getTag(), testTag.getTag());
        }
    }
    
    @Test
    public void testGetAllTagsForStatuses(List<Tag> tags) {
        for (Tag tag: tags) {
            tagDao.addTag(tag);
        }
        final List<Tag> allActiveTags = tagDao.getAllTagsForStatuses(Status.active);
        assertEquals(tags.size(), allActiveTags.size());
        for (int index = 0; index < tags.size(); index += 1) {
            final Tag actualTag = tags.get(index);
            final Tag testTag = allActiveTags.get(index);
            assertEquals(actualTag.getTag(), testTag.getTag());
        }
    }

    /**
     * Test of deleteTagById method, of class TagDaoDB.
     */
    @Test
    public void testDeleteTagById(List<Tag> tags) {
        final int NO_TAGS = 0;
        for (Tag tag: tags) {
            final Tag tagAdded = tagDao.addTag(tag);
            tagDao.deleteTagById(tagAdded.getTagId());
        }
        final List<Tag> allTags = tagDao.getAllTags();
        assertEquals(NO_TAGS, allTags.size());
    }
    
}
