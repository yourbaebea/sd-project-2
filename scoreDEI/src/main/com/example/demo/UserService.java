package com.example.demo;

import com.example.data.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service    
public class UserService
{    
    @Autowired    
    private UserRepository userRepository;

    public List<User> getAllUsers()
    {
        List<User>userRecords = new ArrayList<>();
        userRepository.findAll().forEach(userRecords::add);
        return userRecords;
    }

    public boolean addUser(User user)
    {
        Optional<User> u= userRepository.findByEmail(user.getEmail());
        if(u.isPresent()) return false;
        System.out.println(user);
        userRepository.save(user);
        return true;
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

    public User checkLogin(String email, String password){
        Optional<User> u= userRepository.findByEmail(email);
        if(u.isPresent() && u.get().login(password)) return u.get();
        return null;

    }

}    