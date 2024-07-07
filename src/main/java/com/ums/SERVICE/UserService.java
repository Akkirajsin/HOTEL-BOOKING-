package com.ums.SERVICE;

import com.ums.PAYLOAD.LoginDto;
import com.ums.PAYLOAD.UserDto;

import java.util.Optional;

public interface UserService {
    String  verifyLogin(LoginDto logInDto);

    //CRUD OPERATION
    public UserDto addUser(UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);
    void deleteUser(Long userId);
    Optional<UserDto> getUserById(Long id);
}
