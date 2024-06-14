package com.kukilabs.demoJDBC.user.service;

import com.kukilabs.demoJDBC.exceptions.ApplicationException;
import com.kukilabs.demoJDBC.exceptions.DataNotFoundException;
import com.kukilabs.demoJDBC.user.dto.EditProfileDto;
import com.kukilabs.demoJDBC.user.dto.PasswordDto;
import com.kukilabs.demoJDBC.user.dto.PinDto;
import com.kukilabs.demoJDBC.user.dto.RegisterDto;
import com.kukilabs.demoJDBC.user.entity.User;
import com.kukilabs.demoJDBC.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl( UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    @Transactional
    public void createUser(User user){
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new DataNotFoundException("User not found");
        }
        return user.orElse(null);
    }

    @Override
    @Transactional
    public User register(RegisterDto user){
        User newUser = user.toEntity();
        var password = passwordEncoder.encode(user.getPassword());
        Instant now = Instant.now();
        newUser.setPassword(password);
        newUser.setPin(" ");
        newUser.setProfileImgUrl(" ");
        newUser.setCreatedAt(now);
        newUser.setUpdatedAt(now);
        newUser.setQuotes(" ");
        return userRepository.save(newUser);
    }

    @Override
    public void changePassword(PasswordDto passwordDto, Long userId){
        Instant now = Instant.now();
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new DataNotFoundException("User not found");
        }

        if(!passwordEncoder.matches(passwordDto.getCurrentPassword(), user.get().getPassword())){
            throw new ApplicationException("Password not match");
        }


        var encryptedPassword = passwordEncoder.encode(passwordDto.getNewPassword());
        User newUser = user.get();
        newUser.setUpdatedAt(now);
        newUser.setPassword(encryptedPassword);
        userRepository.save(newUser);
    }

    @Override
    public void setUpPin(PinDto pin, Long userId){
        Instant now = Instant.now();
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new DataNotFoundException("User not found");
        }

        var encryptedPin = passwordEncoder.encode(pin.getPin());
        User newUser = user.get();
        newUser.setUpdatedAt(now);
        newUser.setPin(encryptedPin);
        userRepository.save(newUser);
    }

    @Transactional
    public void editProfile(EditProfileDto editProfileDto, Long userId){
        Instant now = Instant.now();
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new DataNotFoundException("User not found");
        }

        User newUser = user.get();
        newUser.setName(editProfileDto.getName());
        newUser.setEmail(editProfileDto.getEmail());
        newUser.setQuotes(editProfileDto.getQuotes());
        newUser.setProfileImgUrl(editProfileDto.getProfilePhotoUrl());
        newUser.setLanguageId(editProfileDto.getLanguageId());
        newUser.setUpdatedAt(now);
        userRepository.save(newUser);
    }
}
