package com.example.trainogram.services;

import com.example.trainogram.dto.PostDTO;
import com.example.trainogram.entity.Post;
import com.example.trainogram.entity.SponsorPost;
import com.example.trainogram.exception.Status427UserHasNotRootException;
import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.exception.Status436PostNotFoundException;

import java.io.IOException;

public interface SponsorPostService {

    SponsorPost createSponsorPost(PostDTO postDTO, Long sponsorId, String token) throws IOException, Status430UserNotFoundException;
    SponsorPost findSponsorPostByPostId(Long postId);
    SponsorPost findSponsorPostByIdLike(Long likeId);
    void deleteSponsorPost(Long postId, String token) throws Status436PostNotFoundException, Status427UserHasNotRootException;
}
