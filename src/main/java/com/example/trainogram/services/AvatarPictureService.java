package com.example.trainogram.services;

import com.example.trainogram.entity.Avatar;
import com.example.trainogram.entity.Picture;
import com.example.trainogram.entity.Post;
import com.example.trainogram.entity.User;
import com.example.trainogram.exception.Status430UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AvatarPictureService {

    List<Avatar> uploadImageToFileSystem(List<MultipartFile> file, String token) throws IOException, Status430UserNotFoundException;
    Optional<Avatar> findAvatarById(Long avatarId);
}
