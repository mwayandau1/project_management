package com.code.controller;


import com.code.config.JwtProvider;
import com.code.model.User;
import com.code.repository.UserRepository;
import com.code.request.LoginRequest;
import com.code.response.AuthResponse;
import com.code.service.CustomUserDetailsImpl;
import com.code.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CustomUserDetailsImpl customUserDetails;

    @Autowired
    private SubscriptionService subscriptionService;



    @PostMapping("/register")
    ResponseEntity<AuthResponse> createUser(@RequestBody User user) throws Exception {
        User userAlreadyExist = userRepository.findByEmail(user.getEmail());
        if(userAlreadyExist != null){
            throw  new Exception("User with this email already exist");
        }
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());


        User savedUer = userRepository.save(newUser);

        subscriptionService.createSubscription(savedUer);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("User created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws Exception{
       String username = loginRequest.getEmail();
       String password = loginRequest.getPassword();
       Authentication authentication = authenticate(username, password);
       SecurityContextHolder.getContext().setAuthentication(authentication);
       String jwt = JwtProvider.generateToken(authentication);

       AuthResponse response = new AuthResponse();
       response.setMessage("Login successful");
       response.setJwt(jwt);
        return new ResponseEntity<> (response, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if(userDetails == null){
            throw  new BadCredentialsException("Invalid password or email");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw  new BadCredentialsException("Invalid password or email");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
