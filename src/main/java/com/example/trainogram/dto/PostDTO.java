package com.example.trainogram.dto;

import com.example.trainogram.entity.Picture;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class PostDTO {

    private String text;
    private List <MultipartFile> multipartFile;

}
