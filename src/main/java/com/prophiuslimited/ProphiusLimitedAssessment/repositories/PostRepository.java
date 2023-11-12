package com.prophiuslimited.ProphiusLimitedAssessment.repositories;

import com.prophiuslimited.ProphiusLimitedAssessment.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

//Spring Data JPA
public interface PostRepository extends JpaRepository<Post, Long> {
}
