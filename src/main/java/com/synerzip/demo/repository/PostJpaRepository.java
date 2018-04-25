package com.synerzip.demo.repository;

import com.synerzip.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Integer> {
}
