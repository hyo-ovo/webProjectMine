package MINE.repository;

import MINE.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    @Query("SELECT c FROM Comment c WHERE c.article.id = :articleId ORDER BY c.createdAt ASC")
    List<Comment> findByArticleIdOrderByCreatedAtAsc(@Param("articleId") Long articleId);
    
    void deleteByArticleId(Long articleId);
} 