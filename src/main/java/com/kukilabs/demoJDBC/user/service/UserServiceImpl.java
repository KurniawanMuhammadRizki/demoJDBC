package com.kukilabs.demoJDBC.user.service;

import com.kukilabs.demoJDBC.user.entity.User;
import com.kukilabs.demoJDBC.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl( UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public void createUser(User user){
        userRepository.save(user);
    }


}
