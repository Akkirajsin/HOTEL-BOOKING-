package com.ums.SERVICE;

import com.ums.ENTITY.AppUser;
import com.ums.PAYLOAD.LoginDto;
import com.ums.PAYLOAD.UserDto;
import com.ums.REPOSITORY.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService {

    private AppUserRepository userRepository;
    private JWTService jwtService;

    public UserServiceImpl(AppUserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserDto addUser(@RequestBody UserDto userDto) {
        AppUser user=mapToEntity(userDto);
        AppUser savedUser=userRepository.save(user);
        UserDto dto=mapToDto(savedUser);
        return dto;
    }

    @Override
    public String verifyLogin(LoginDto logInDto) {
        Optional<AppUser> opUser= userRepository.findByUsername(logInDto.getUsername());
        if (opUser.isPresent()){
            AppUser user= opUser.get();
            if( BCrypt.checkpw(logInDto.getPassword(),user.getPassword()));
           return jwtService.generateToken(user);
        }
        return null;
    }
    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        Optional<AppUser> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            user.setName(userDto.getName());
            user.setUsername(userDto.getUsername());
            user.setEmailId(userDto.getEmailId());
            // Update other fields as needed
            AppUser updatedUser = userRepository.save(user);
            return mapToDto(updatedUser);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId);
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        Optional<AppUser> user = userRepository.findById(id);
        return user.map(this::mapToDto);

    }

    UserDto mapToDto(AppUser user){
        UserDto dto=new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmailId(user.getEmailId());
        dto.setUserRole(user.getUserRole());
        dto.setPassword(user.getPassword());
        return  dto;

    }
    AppUser mapToEntity(UserDto userDto) {
        AppUser user = new AppUser();
        user.setName(userDto.getName()); // Set the name on the user object
        user.setUsername(userDto.getUsername()); // Set the username on the user object
        user.setEmailId(userDto.getEmailId());// Set the emailId on the user object
        user.setUserRole("ROLE_USER");
        user.setPassword(BCrypt.hashpw(userDto.getPassword(),BCrypt.gensalt(10))); // Set the password on the user object
        return user;
    }
}
