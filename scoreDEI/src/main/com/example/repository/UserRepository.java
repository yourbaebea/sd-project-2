package com.example.repository;

import com.example.data.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("select s from User s where s.name like %?1")
    public List<User> findByNameEndsWith(String chars);

    @Query("select distinct u from User u where u.email=?1")
    public Optional<User> findByEmail(String email);
}    