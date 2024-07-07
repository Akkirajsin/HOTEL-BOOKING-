package com.ums.CONTROLLER;

import com.ums.ENTITY.AppUser;
import com.ums.PAYLOAD.JwtResponse;
import com.ums.PAYLOAD.LoginDto;
import com.ums.PAYLOAD.UserDto;
import com.ums.SERVICE.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{addUser}")
    public ResponseEntity<UserDto> addUser( @RequestBody UserDto userDto){ //the message  when data invalid is taken by the bindingResult
        UserDto dto = userService.addUser(userDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto logInDto ){
        String token=userService.verifyLogin(logInDto);
        if(token!=null){
            JwtResponse response=new JwtResponse();
            response.setToken(token);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
         return new ResponseEntity<>("INVALID USER...",HttpStatus.UNAUTHORIZED);
    }
    //http://localhost:8080/api/v1/auth//getCustomer/id
    // pass the id no at the palce of id in url
    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UserDto> userDto = userService.getUserById(id);
        if (userDto.isPresent()) {
            return ResponseEntity.ok(userDto.get()); // IF PRESENT THE GET THE DATA FROM THE DATABASE
        } else {
            return  new ResponseEntity<>("NO SUCH ID EXIST",HttpStatus.UNAUTHORIZED); // IF NOT PRESENT THEN DISPLAY THE MESSAGE
        }
    }
    //http://localhost:8080/api/v1/auth/updateCustomer/id
    // pass the jason object in the postmen body
    // Endpoint to update user by ID
    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);
        return new ResponseEntity<>("THE CUSTOMER IS UPDATED",HttpStatus.OK);
    }
    //http://localhost:8080/api/v1/auth/deleteCustomer/id
    // pass the id no at the palace of id in url
    // Endpoint to delete user by ID
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new  ResponseEntity<>("THE CUSTOMER IS DELETED",HttpStatus.OK);
    }
    @PostMapping("/profile")
    public AppUser getCurrentProfile(@AuthenticationPrincipal AppUser user){

        return user;
    }

}
