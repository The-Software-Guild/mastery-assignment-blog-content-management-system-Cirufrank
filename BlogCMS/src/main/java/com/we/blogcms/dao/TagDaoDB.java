/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Status;
import com.we.blogcms.model.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ciruf
 */
@Repository
public class TagDaoDB implements TagDao {
    @Autowired
    JdbcTemplate jdbc;
//    private int tagId;
//    private String tag;
//    private LocalDateTime createdAt;
//    private Status status;
    @Override
    @Transactional
    public Tag addTag(Tag tag) {
        final String INSERT_TAG_SQL = "INSERT INTO tag (tag) "
                + "VALUES(?);";
        try {
            jdbc.update(INSERT_TAG_SQL,
                tag.getTag());
        } catch(DataAccessException error) {
            return null;
        }
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        tag = getTagById(newId);
        return tag;
        
    }
    @Override
    public Tag getTagById(int tagId) {
        final String GET_TAG_BY_ID_SQL = "SELECT * FROM tag WHERE "
                + "tagId = ?;";
        try {
            final Tag tag = jdbc.queryForObject(GET_TAG_BY_ID_SQL, new TagMapper(), tagId);
            return tag;
        } catch(DataAccessException error) {
            return null;
        }      
    }
    @Override
    public List<Tag> getAllTags() {
        final String GET_ALL_TAGS_SQL = "SELECT * FROM tag;";
        final List<Tag> tags = jdbc.query(GET_ALL_TAGS_SQL, new TagMapper());
        return tags;
    }
    @Override
    public void deleteTagById(int tagId) {
        final String DELETE_TAG_BY_ID_SQL = "DELETE FROM tag WHERE tagId = ?;";
        jdbc.update(DELETE_TAG_BY_ID_SQL, tagId);
    }
    
    public static final class TagMapper implements RowMapper<Tag> {
    
    @Override
    public Tag mapRow(ResultSet rs, int index) throws SQLException {
        Tag tag = new Tag();
        tag.setTagId(rs.getInt("tagId"));
        tag.setTag(rs.getString("tag"));
        tag.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
        tag.setStatus(Status.valueOf(rs.getString("status")));
        
        return tag;
    }
}
}
