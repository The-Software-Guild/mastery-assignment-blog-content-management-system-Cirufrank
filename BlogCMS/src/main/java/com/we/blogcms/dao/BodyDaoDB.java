/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Body;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class BodyDaoDB implements BodyDao {
    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    @Transactional
    public Body addBody(Body body) {
        final String INSERT_BODY_SQL = "INSERT INTO body(body) VALUES(?);";
        jdbc.update(INSERT_BODY_SQL, body.getBody());
        final int bodyId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        body.setBodyId(bodyId);
        return body;
    }
    
    @Override
    public List<Body> getAllPostBodies() {
        final String GET_ALL_POST_BODIES_SQL = "SELECT * FROM body;";
        try {
            List<Body> postBodies = jdbc.query(GET_ALL_POST_BODIES_SQL, 
                new BodyMapper());
            return postBodies;
        } catch(DataAccessException error) {
            return null;
        }
    }
    
    @Override
    public Body getBodyById(int bodyId) {
        final String GET_BODY_SQL = "SELECT * FROM body WHERE bodyId = ?";
        try {
            final Body body = jdbc.queryForObject(GET_BODY_SQL, new BodyMapper(), bodyId);
            return body;
        } catch(DataAccessException error) {
            return null;
        }
    }
    
    @Override
    public Body getPostBody(int postId) {
        //NEEDS TO BE TESTED
        final String GET_POST_BODY_SQL = "SELECT p.* FROM body b INNER JOIN "
                + "postbody pb ON b.bodyId = pb.bodyId WHERE "
                + "p.postId = ?;";
        final Body postBody = jdbc.queryForObject(GET_POST_BODY_SQL, new BodyMapper(), postId);
        return postBody;
    }
    
    @Override
    @Transactional
    public Body updateBody(Body body) {
        final String UPDATE_BODY_SQL = "UPDATE body SET body = ? WHERE "
                + "bodyId = ?;";
        try {
            jdbc.update(UPDATE_BODY_SQL,
                body.getBody(),
                body.getBodyId());
            final Body updatedBody = getBodyById(body.getBodyId());
            return updatedBody;
        } catch (DataAccessException error) {
            return null;
        }
    }
    
    @Override
    public void deleteBodyById(int bodyId) {
        final String DELETE_BODY_SQL = "DELETE FROM body WHERE "
                + "bodyId = ?;";
        jdbc.update(DELETE_BODY_SQL, bodyId);
    }
    
    public static class BodyMapper implements RowMapper<Body> {
        @Override
        public Body mapRow(ResultSet rs, int index) throws SQLException {
            final Body body = new Body();
            body.setBodyId(rs.getInt("bodyId"));
            body.setBody(rs.getString("body"));
            return body;
        }
    }
}
