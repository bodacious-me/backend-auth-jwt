package com.gymbroz.DBService.repo;

import com.gymbroz.DBService.model.User;

public interface CustomUserRepository {
User findUserByEmail(String email);
}
