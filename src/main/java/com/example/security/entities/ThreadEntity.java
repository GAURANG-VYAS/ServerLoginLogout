package com.example.security.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Document(collection ="Thread")

public class ThreadEntity {

    @Id
    private String id;
    private String title;
    private String by;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBy() {
        return by;
    }

    public void setBY(String by) {
        this.by = by;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public ArrayList<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<PostEntity> posts) {
        this.posts = posts;
    }

    private String active;
    private ArrayList<PostEntity> posts;
}
