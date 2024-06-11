package com.kukilabs.demoJDBC.user.repository;

import com.kukilabs.demoJDBC.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
}
