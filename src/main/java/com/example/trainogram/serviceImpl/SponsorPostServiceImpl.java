package com.example.trainogram.serviceImpl;

import com.example.trainogram.dto.PostDTO;
import com.example.trainogram.entity.SponsorPost;
import com.example.trainogram.exception.Status427UserHasNotRootException;
import com.example.trainogram.exception.Status430UserNotFoundException;
import com.example.trainogram.exception.Status436PostNotFoundException;
import com.example.trainogram.repositories.SponsorPostRepository;
import com.example.trainogram.services.PostService;
import com.example.trainogram.services.SponsorPostService;
import com.example.trainogram.services.UserService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SponsorPostServiceImpl implements SponsorPostService {

    private final PostService postService;

    private final SponsorPostRepository sponsorPostRepository;

    private final UserService userService;


    public SponsorPostServiceImpl(PostService postService, SponsorPostRepository sponsorPostRepository, UserService userService) {
        this.postService = postService;
        this.sponsorPostRepository = sponsorPostRepository;
        this.userService = userService;
    }

    @Override
    public SponsorPost createSponsorPost(PostDTO postDTO, Long sponsorId, String token) throws IOException, Status430UserNotFoundException {

        return sponsorPostRepository.save(SponsorPost.builder()
                .post(postService.createPost(postDTO,token))
                .sponsorUser(userService.findById(sponsorId))
                .build());

    }


      public SponsorPost findSponsorPostByPostId(Long postId){
      return sponsorPostRepository.findSponsorPostByPostId(postId);

    }

    @Override
    public SponsorPost findSponsorPostByIdLike(Long likeId) {
        return sponsorPostRepository.findSponsorPostsByIdLike(likeId);
    }

    @Override
    public void deleteSponsorPost(Long sponsorPostId, String token) throws Status436PostNotFoundException, Status427UserHasNotRootException {
        Long userId = userService.getAuthenticatedUser(token).get().getId();
        SponsorPost sponsorPost = sponsorPostRepository.findById(sponsorPostId).orElseThrow(() -> new Status436PostNotFoundException("SponsorPost not found"));

        if (userId.equals(sponsorPost.getPost().getUser().getId())){
            sponsorPostRepository.deleteById(sponsorPost.getId());
        }else {
            throw new Status427UserHasNotRootException("User with id " +userId+" has not root");
        }

    }
}
