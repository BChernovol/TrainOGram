package com.example.trainogram.services;

import com.example.trainogram.dto.CommentDTO;
import com.example.trainogram.entity.Comment;
import com.example.trainogram.exception.Status420CommentPostIdNotFoundException;
import com.example.trainogram.exception.Status427UserHasNotRootException;
import com.example.trainogram.exception.Status439TextIsEmptyException;

import java.util.Optional;

public interface CommentService {
     Comment createComment(Long id,CommentDTO commentDTO, String token) throws Status420CommentPostIdNotFoundException, Status439TextIsEmptyException;

     void deleteComment(Long id, String token) throws Status420CommentPostIdNotFoundException, Status427UserHasNotRootException;

     Comment findById(Long id) throws Status420CommentPostIdNotFoundException;
     void updateComment(Long id, CommentDTO commentDTO, String token) throws Status427UserHasNotRootException;
}
