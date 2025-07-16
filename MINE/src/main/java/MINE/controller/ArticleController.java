package MINE.controller;

import MINE.domain.Article;
import MINE.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleRepository articleRepository;

    @GetMapping("/write")
    public String showWriteForm() {
        return "write"; // write.html 렌더링
    }

    @PostMapping("/write")
    public String writeArticle(@RequestParam String title,
                               @RequestParam String content,
                               Model model) {
        Article article = new Article(null, title, content);
        articleRepository.save(article);
        return "redirect:/"; // 작성 후 홈으로 이동
    }
}
