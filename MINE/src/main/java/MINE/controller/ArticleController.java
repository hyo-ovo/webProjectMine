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

    // ✅ 글쓰기 폼 페이지
    @GetMapping("/articles/new")
    public String showNewArticleForm() {
        return "newArticle"; // templates/newArticle.html
    }

    // ✅ 글 작성 처리
    @PostMapping("/articles")
    public String createArticle(@RequestParam String title,
                                @RequestParam String content,
                                Principal principal) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setCreatedAt(LocalDateTime.now());

        // 작성자 이름 설정
        if (principal != null) {
            article.setAuthor(principal.getName());
        } else {
            article.setAuthor("anonymous");
        }

        articleRepository.save(article);
        return "redirect:/articles";
    }

    // ✅ 글 목록 보기
    @GetMapping("/articles")
    public String listArticles(Model model) {
        List<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        model.addAttribute("articles", articles);
        return "list"; // templates/list.html
    }

    // ✅ 글 상세 보기
    @GetMapping("/articles/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다: id=" + id));
        model.addAttribute("article", article);
        return "articleDetail"; // templates/articleDetail.html
    }
}
