package com.example.trainogram.repositories;

import com.example.trainogram.entity.Post;
import com.example.trainogram.entity.SponsorPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorPostRepository extends JpaRepository<SponsorPost,Long> {

    SponsorPost findSponsorPostByPostId(Long postId);

    SponsorPost findSponsorPostsByIdLike(Long likeId);

}
