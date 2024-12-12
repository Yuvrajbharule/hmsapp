package com.hmsapp.controller;

import com.hmsapp.entity.User;
import com.hmsapp.payload.JwtToken;
import com.hmsapp.payload.LoginDto;
import com.hmsapp.payload.UserRequestDto;

import com.hmsapp.payload.UserResponseDto;
import com.hmsapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser( @Valid @RequestBody UserRequestDto dto)
    {
       Optional<User> opUsername = userService.findByUsername(dto);
       if(opUsername.isPresent())
       {

            return new ResponseEntity<>("Username is Already exist",HttpStatus.INTERNAL_SERVER_ERROR);
       }
       Optional<User> opMobile = userService.findByMobile(dto);
       if(opMobile.isPresent())
       {

           return new ResponseEntity<>("Mobile Number is Already Exist",HttpStatus.INTERNAL_SERVER_ERROR);

       }
       Optional<User> opEmail = userService.findByEmail(dto);
       if(opEmail.isPresent())
       {
           return new ResponseEntity<>("Email Already Exist",HttpStatus.INTERNAL_SERVER_ERROR);
       }

       UserResponseDto responseDto = userService.CreateUser(dto);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);


    }

    @PostMapping("/property/sign-up")
    public ResponseEntity<?> createPropertyOwnerAccount( @Valid @RequestBody UserRequestDto dto)
    {
        Optional<User> opUsername = userService.findByUsername(dto);
        if(opUsername.isPresent())
        {

            return new ResponseEntity<>("Username is Already exist",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opMobile = userService.findByMobile(dto);
        if(opMobile.isPresent())
        {

            return new ResponseEntity<>("Mobile Number is Already Exist",HttpStatus.INTERNAL_SERVER_ERROR);

        }
        Optional<User> opEmail = userService.findByEmail(dto);
        if(opEmail.isPresent())
        {
            return new ResponseEntity<>("Email Already Exist",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserResponseDto responseDto = userService.CreatePropertyOwner(dto);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);


    }

    @PostMapping("/blog/sign-up")
    public ResponseEntity<?> createContentManagerAccount( @Valid @RequestBody UserRequestDto dto)
    {
        Optional<User> opUsername = userService.findByUsername(dto);
        if(opUsername.isPresent())
        {

            return new ResponseEntity<>("Username is Already exist",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<User> opMobile = userService.findByMobile(dto);
        if(opMobile.isPresent())
        {

            return new ResponseEntity<>("Mobile Number is Already Exist",HttpStatus.INTERNAL_SERVER_ERROR);

        }
        Optional<User> opEmail = userService.findByEmail(dto);
        if(opEmail.isPresent())
        {
            return new ResponseEntity<>("Email Already Exist",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserResponseDto responseDto = userService.createBlogManager(dto);
        return new ResponseEntity<>(responseDto,HttpStatus.CREATED);


    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginDto dto)
    {
        String token = userService.loginUser(dto);
        if(token!=null)
        {
            JwtToken  jwttoken = new JwtToken();
            jwttoken.setToken(token);
            jwttoken.setType("JWT");
            return new ResponseEntity<>(jwttoken,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid",HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
