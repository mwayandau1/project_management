package com.code.service;


import com.code.config.JwtProvider;
import com.code.model.User;
import com.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String token) throws Exception {
        String email = JwtProvider.getEmailFromToken(token);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("No user found with this email");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
       Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw  new Exception("User does not exist");
        }
        return user.get();
    }

    @Override
    public User updateUsersProjectSize(User user, int number) throws Exception {

        user.setProjectSize(user.getProjectSize() + number);
        return  userRepository.save(user);

    }
}
