package com.example.security.controllers;

import com.example.security.entities.AppUserEntity;
import com.example.security.entities.PostEntity;
import com.example.security.repositories.AppUserRepository;
import com.example.security.repositories.ProblemRepository;
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
public class ProblemController {
    private ProblemRepository problem;

    @Autowired
    MongoOperations mongoOperations;


    public ProblemController(ProblemRepository problem) { this.problem = problem;}

    @GetMapping(value = "/posts/{id}")
    public ArrayList<PostEntity> getPosts(@PathVariable(value="id") String id){
        ProblemEntity prob = problem.findFirstById(id);
        if(prob!=null)
            return prob.getPosts();
        else return null;
    }

    @PostMapping(value = "/createpost/{id}")
    public ResponseEntity<PostEntity> postData(@RequestBody PostEntity post,@PathVariable(value="id") String id){
        Update args = new Update();
        args.addToSet("posts",post);
        Query query = new Query(Criteria.where("id").is(id));
        System.out.println("**** Post Details *****");
        System.out.println("Post Author : "+post.getAuthor());
        System.out.println("Post Text : "+post.getText());
        System.out.println("Post Date : "+post.getDate());
        //System.out.println(args.toString());
        mongoOperations.findAndModify(query,args,ProblemEntity.class);
        return new ResponseEntity<>(post, HttpStatus.CREATED);

    }

}
