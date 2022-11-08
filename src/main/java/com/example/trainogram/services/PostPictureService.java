package com.example.trainogram.services;

import com.example.trainogram.entity.Picture;
import com.example.trainogram.entity.Post;
import com.example.trainogram.exception.Status436PostNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostPictureService {

   List<Picture> uploadImageToFileSystem(Post post, List<MultipartFile> file, String token) throws IOException;

   void savePicture(Picture picture);

   void deletePicture(Long id);
}
