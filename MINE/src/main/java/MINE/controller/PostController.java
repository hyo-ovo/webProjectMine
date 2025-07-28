package MINE.controller;

import MINE.domain.Post;
import MINE.domain.User;
import MINE.service.PostService;
import MINE.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    // 글 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.findById(id);
        if (!post.getAuthor().getUsername().equals(userDetails.getUsername())) {
            return "redirect:/?error=unauthorized";
        }
        model.addAttribute("post", post);
        return "editArticle";
    }

    // 글 수정 처리
    @PostMapping("/{id}/edit")
    public String editSubmit(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String content,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        postService.updatePost(id, title, content, currentUser);
        return "redirect:/";
    }

    // 글 삭제 처리
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        postService.deletePost(id, currentUser);
        return "redirect:/";
    }
}
