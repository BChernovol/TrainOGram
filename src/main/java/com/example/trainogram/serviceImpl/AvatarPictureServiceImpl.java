package com.example.trainogram.serviceImpl;

import com.example.trainogram.entity.Avatar;
import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.repositories.AvatarRepository;
import com.example.trainogram.services.AvatarPictureService;
import com.example.trainogram.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AvatarPictureServiceImpl implements AvatarPictureService {

    private final AvatarRepository avatarRepository;
    private final UserService userService;

    public AvatarPictureServiceImpl(AvatarRepository avatarRepository, UserService userService) {
        this.avatarRepository = avatarRepository;
        this.userService = userService;
    }

    @Value("${path.folder}")
    private String FOLDER_PATH;
    @Value("${path.folder.user}")
    private String FOLDER_PATH_USER;

    public static void createFoldersToAvatar(Long userId, String folderPath, String folderPathUser){
        File fileForImages = new File(folderPath);
        String path = folderPathUser+ " " + userId + "/";
        File fileForImagesUser = new File(path);

        if (fileForImages.mkdirs()) {
            System.out.println("Directory is created");
        }
        if (!fileForImagesUser.exists()) {
            fileForImagesUser.mkdirs();
            System.out.println("Directory is created");
        }
    }


    @Override
    public List<Avatar> uploadImageToFileSystem(List<MultipartFile> file, String token) throws IOException, Status430UserNotFoundException {
        Long userId = userService.getAuthenticatedUser(token).get().getId();

        String uuidFile = UUID.randomUUID().toString();

        createFoldersToAvatar(userId, FOLDER_PATH, FOLDER_PATH_USER);

        String path = FOLDER_PATH_USER+ " " + userId + "/";

        List<Avatar> picturesAvatar = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            String filePath = path + uuidFile + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(filePath));

            picturesAvatar.add(avatarRepository.save(Avatar.builder()
                    .user(userService.findById(userId))
                    .path(path)
                    .build()));

        }
        return picturesAvatar;
    }

    public Optional<Avatar> findAvatarById(Long avatarId){
        return Optional.ofNullable(avatarRepository.findAvatarById(avatarId));
    }
}

