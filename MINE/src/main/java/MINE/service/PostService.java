package MINE.service;

import MINE.domain.Post;
import MINE.domain.User;
import MINE.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("글을 찾을 수 없습니다."));
    }

    public void updatePost(Long postId, String title, String content, User currentUser) {
        Post post = findById(postId);
        if (!post.getAuthor().getUsername().equals(currentUser.getUsername())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
    }

    public void deletePost(Long postId, User currentUser) {
        Post post = findById(postId);
        if (!post.getAuthor().getUsername().equals(currentUser.getUsername())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        postRepository.delete(post);
    }
}
