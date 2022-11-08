package com.example.trainogram.conrtollers;


import com.example.trainogram.dto.AvatarDTO;
import com.example.trainogram.dto.UserDTO;
import com.example.trainogram.entity.User;
import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.exception.Status433LikeAlreadyExistsException;
import com.example.trainogram.exception.Status438AvatarNotFoundException;
import com.example.trainogram.services.AvatarPictureService;
import com.example.trainogram.services.LikeAvatarService;
import com.example.trainogram.serviceImpl.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;
    private final  AvatarPictureService avatarPictureService;

   private final LikeAvatarService likeAvatarService;

    @Autowired
    public UserController(UserServiceImpl userService, ModelMapper modelMapper, AvatarPictureService avatarPictureService, LikeAvatarService likeAvatarService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.avatarPictureService = avatarPictureService;
        this.likeAvatarService = likeAvatarService;
    }

    @GetMapping("/get-all")
    @ResponseBody
    public List<User> getUsers() throws Status430UserNotFoundException {
        return userService.getAllUsers();
    }


    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody UserDTO userDTO){
        userService.save(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/set-avatar")
    public ResponseEntity<?> setAvatar(@RequestHeader("Authorization") String token, @ModelAttribute AvatarDTO avatarDTO) throws Status430UserNotFoundException, IOException {
      avatarPictureService.uploadImageToFileSystem(avatarDTO.getMultipartFile(), token);
      return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("set-avatar-like/{id}")
    public ResponseEntity<?> setLikeAvatar(@RequestHeader("Authorization") String token, @PathVariable("id") Long avatarId) throws Status433LikeAlreadyExistsException, Status438AvatarNotFoundException {
      likeAvatarService.setLike(avatarId, token);
      return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public User findById(@PathVariable Long id) throws Status430UserNotFoundException {
        return userService.findById(id);
    }


    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id){
        userService.deleteUsesById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
