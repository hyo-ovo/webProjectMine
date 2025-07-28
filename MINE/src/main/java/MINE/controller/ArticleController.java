package MINE.controller;

import MINE.domain.Article;
import MINE.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    // 글쓰기 폼
    @GetMapping("/articles/new")
    public String showNewArticleForm() {
        return "newArticle";
    }

    // 글 작성 처리
    @PostMapping("/articles")
    public String createArticle(@RequestParam String title,
                                @RequestParam String content,
                                Principal principal) {
        try {
            Article article = new Article();
            article.setTitle(title);
            article.setContent(content);
            article.setCreatedAt(LocalDateTime.now());

            // Null 안전 처리
            if (principal != null && principal.getName() != null) {
                article.setAuthor(principal.getName());
            } else {
                article.setAuthor("anonymous");
            }

            articleRepository.save(article);
            return "redirect:/articles";
        } catch (Exception e) {
            e.printStackTrace(); // 콘솔에서 원인 확인 가능
            return "redirect:/error";
        }
    }

    // 글 목록
    @GetMapping("/articles")
    public String listArticles(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Article> articles;
        if (keyword != null && !keyword.trim().isEmpty()) {
            articles = articleRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
            model.addAttribute("keyword", keyword);
        } else {
            articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
            model.addAttribute("keyword", null);
        }
        model.addAttribute("articles", articles);
        return "list";
    }

    // 글 상세
    @GetMapping("/articles/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다: id=" + id));
        model.addAttribute("article", article);
        return "articleDetail";
    }

    // 글 수정 폼
    @GetMapping("/articles/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, Principal principal) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다: id=" + id));
        
        // 작성자 확인
        if (principal == null || !article.getAuthor().equals(principal.getName())) {
            return "redirect:/articles?error=unauthorized";
        }
        
        model.addAttribute("article", article);
        return "editArticle";
    }

    // 글 수정 처리
    @PostMapping("/articles/{id}/edit")
    public String updateArticle(@PathVariable Long id,
                                @RequestParam String title,
                                @RequestParam String content,
                                Principal principal) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다: id=" + id));
        
        // 작성자 확인
        if (principal == null || !article.getAuthor().equals(principal.getName())) {
            return "redirect:/articles?error=unauthorized";
        }
        
        article.setTitle(title);
        article.setContent(content);
        articleRepository.save(article);
        
        return "redirect:/articles/" + id;
    }

    // 글 삭제 처리
    @PostMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id, Principal principal) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다: id=" + id));
        
        // 작성자 확인
        if (principal == null || !article.getAuthor().equals(principal.getName())) {
            return "redirect:/articles?error=unauthorized";
        }
        
        articleRepository.delete(article);
        return "redirect:/articles";
    }
}
