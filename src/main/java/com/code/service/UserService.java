package com.code.service;

import com.code.model.User;

public interface UserService {

    User findUserProfileByJwt(String token) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User findUserById(Long userId) throws Exception;

    User updateUsersProjectSize(User user, int number) throws Exception;


}
