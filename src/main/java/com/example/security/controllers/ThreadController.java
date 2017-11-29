package com.example.security.controllers;

import com.example.security.entities.AppUserEntity;
import com.example.security.entities.PostEntity;
import com.example.security.entities.ThreadEntity;
import com.example.security.repositories.AppUserRepository;
import com.example.security.repositories.ProblemRepository;
import com.example.security.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.security.entities.ProblemEntity;
import com.example.security.config.AppConfig;
import org.springframework.data.mongodb.core.MongoOperations;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;

@RestController
public class ThreadController {
    private ThreadRepository threadRepo;

    @Autowired
    MongoOperations mongoOperations;

    public ThreadController(ThreadRepository threadRepo){this.threadRepo = threadRepo;}
    @GetMapping(value = "/posts/{id}")
    public ThreadEntity getPosts(@PathVariable(value="id") String id){
        ThreadEntity threadEnt = threadRepo.findFirstById(id);
        return threadEnt;
    }
    @GetMapping(value = "/close/{id}")
    public void closeThread(@PathVariable(value="id") String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoOperations.updateMulti(query,
                Update.update("active", "false"),ThreadEntity.class);

    }
}
