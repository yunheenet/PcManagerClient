package com.yunheenet.pcroom.domain;

import com.yunheenet.pcroom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {}
