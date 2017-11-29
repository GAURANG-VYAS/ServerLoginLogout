package com.example.security.repositories;
import com.example.security.entities.ThreadEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends MongoRepository<ThreadEntity,String> {
    ThreadEntity findFirstById(String Id);
}
