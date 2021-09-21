package com.kadia.kblogserber.mapper;

import com.kadia.kblogserber.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogMapper extends JpaRepository<Blog, Integer> {
    Page<Blog> findAllByTitleContainingAndState(String title, Integer state, Pageable pageable);
    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);
    Blog findOneByIdAndState(Integer id, Integer state);
    long countByState(Integer state);
    @Query("select sum(view) from Blog where state=?1")
    long sumViewByState(Integer state);
}
