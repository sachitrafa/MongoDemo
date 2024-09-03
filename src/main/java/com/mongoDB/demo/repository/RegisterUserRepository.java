package com.mongoDB.demo.repository;

import com.mongoDB.demo.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterUserRepository extends MongoRepository<Member, String> {
}
