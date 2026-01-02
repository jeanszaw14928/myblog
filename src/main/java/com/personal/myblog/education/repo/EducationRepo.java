package com.personal.myblog.education.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.myblog.education.model.Education;

@Repository
public interface EducationRepo extends JpaRepository<Education, Integer>{

}
