/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.model;

import java.util.Objects;

/**
 *
 * @author ciruf
 */
public class Body {
    private int bodyId;
    private String body;

    public int getBodyId() {
        return bodyId;
    }

    public void setBodyId(int bodyId) {
        this.bodyId = bodyId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.bodyId;
        hash = 61 * hash + Objects.hashCode(this.body);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Body other = (Body) obj;
        if (this.bodyId != other.bodyId) {
            return false;
        }
        return Objects.equals(this.body, other.body);
    }

    @Override
    public String toString() {
        return "Body{" + "bodyId=" + bodyId + ", body=" + body + '}';
    }
    
}
