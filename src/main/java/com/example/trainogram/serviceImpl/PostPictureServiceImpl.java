package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.Picture;
import com.example.trainogram.entity.Post;
import com.example.trainogram.repositories.PictureRepository;
import com.example.trainogram.services.PostPictureService;
import com.example.trainogram.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class PostPictureServiceImpl implements PostPictureService {

    private final PictureRepository pictureRepository;

    private final UserService userService;



    public PostPictureServiceImpl(PictureRepository pictureRepository, UserService userService) {
        this.pictureRepository = pictureRepository;
        this.userService = userService;
    }

    @Value("${path.folder}")
    private String FOLDER_PATH;

    @Value("${path.folder.user}")
    private String FOLDER_PATH_USER;

    public List<Picture> uploadImageToFileSystem(Post post , List<MultipartFile> file, String token) throws IOException {
        Long userId = userService.getAuthenticatedUser(token).get().getId();
        String uuidFile = UUID.randomUUID().toString();

        AvatarPictureServiceImpl.createFoldersToAvatar(userId,FOLDER_PATH, FOLDER_PATH_USER);

        String path = FOLDER_PATH_USER+ " " + userId + "/";

        List<Picture> picturesPost = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String filePath = path + uuidFile + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(filePath));

                    picturesPost.add(pictureRepository.save(Picture.builder()
                            .post(post)
                            .path(filePath)
                            .contentType(multipartFile.getContentType())
                            .createDate(new Date())
                            .build()));
        }
        return picturesPost;
    }

    public void deletePicture(Long postId){
        pictureRepository.deletePictureByPostId(postId);
    }
    @Override
    public void savePicture(Picture picture) {
        pictureRepository.save(picture);
    }


}
