package com.example.trainogram.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class AvatarDTO {
    private List<MultipartFile> multipartFile;
}
