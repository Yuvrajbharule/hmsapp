package com.hmsapp.service;

import com.hmsapp.entity.User;

import com.hmsapp.payload.LoginDto;
import com.hmsapp.payload.UserRequestDto;
import com.hmsapp.payload.UserResponseDto;
import com.hmsapp.repository.UserRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private JWTService jwtService;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, JWTService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }


    public Optional<User> findByUsername(UserRequestDto dto) {

        User user = mapToEntity(dto);
        return userRepository.findByUsername(user.getUsername());
    }

    public Optional<User> findByMobile(UserRequestDto dto) {

        User user = mapToEntity(dto);
        return userRepository.findByMobile(user.getMobile());
    }

    public User mapToEntity(UserRequestDto dto)
    {
        User user = modelMapper.map(dto,User.class);
        return user;
    }

    public UserResponseDto mapToDto(User user)
    {
        UserResponseDto dto = modelMapper.map(user,UserResponseDto.class);
        return dto;
    }

    public User mapToEntity(LoginDto dto)
    {
        return modelMapper.map(dto,User.class);
    }


    public Optional<User> findByEmail(UserRequestDto dto) {

        User user = mapToEntity(dto);
        return userRepository.findByEmail(user.getEmail());
    }

    public UserResponseDto CreateUser(UserRequestDto dto) {

        User user = mapToEntity(dto);
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        user.setRole("ROLE_USER");
        User u = userRepository.save(user);
        UserResponseDto userdto =  mapToDto(u);
        userdto.setDate(new Date());
        return userdto;
    }

    public String loginUser(LoginDto dto) {

        User user = mapToEntity(dto);
        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        User u = opUsername.get();
        if(BCrypt.checkpw(dto.getPassword(),u.getPassword()))
        {
            return jwtService.generateToken(user.getUsername());
        }
        return null;

    }

    public UserResponseDto CreatePropertyOwner(@Valid UserRequestDto dto) {
        User user = mapToEntity(dto);
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        user.setRole("ROLE_OWNER");
        User u = userRepository.save(user);
        UserResponseDto userdto =  mapToDto(u);
        userdto.setDate(new Date());
        return userdto;

    }

    public UserResponseDto createBlogManager(@Valid UserRequestDto dto) {
        User user = mapToEntity(dto);
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        user.setRole("ROLE_BLOGMANAGER");
        User u = userRepository.save(user);
        UserResponseDto userdto =  mapToDto(u);
        userdto.setDate(new Date());
        return userdto;
    }
}
