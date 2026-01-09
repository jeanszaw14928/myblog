package com.personal.myblog.post.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.myblog.post.model.PostModel;

@Repository
public interface PostRepo extends JpaRepository<PostModel,Long>{

}
