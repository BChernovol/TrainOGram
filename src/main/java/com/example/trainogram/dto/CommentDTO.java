package com.example.trainogram.dto;

import com.example.trainogram.entity.Post;
import com.example.trainogram.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
public class CommentDTO {

    private String text;

}
