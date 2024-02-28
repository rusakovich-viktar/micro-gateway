package by.clevertec.gateway.controller.api.v1;

import by.clevertec.gateway.client.NewsClient;
import by.clevertec.gateway.dto.request.NewsRequestDto;
import by.clevertec.gateway.dto.response.CommentListResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NewsController {

    private final NewsClient newsClient;

    @PostMapping("/news")
    ResponseEntity<NewsResponseDto> createNews(@Valid @RequestBody NewsRequestDto newsRequestDto) {
        return newsClient.createNews(newsRequestDto);
    }


    @GetMapping("/news/{id}")
    ResponseEntity<NewsResponseDto> getNewsById(@PathVariable Long id) {
        return newsClient.getNewsById(id);
    }


    @PutMapping("/news/{id}")
    ResponseEntity<NewsResponseDto> updateNews(@PathVariable Long id,
                                               @Valid @RequestBody NewsRequestDto newsRequestDto) {
        return newsClient.updateNews(id, newsRequestDto);
    }


    @DeleteMapping("/news/{id}")
    ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsClient.deleteNews(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/news")
    ResponseEntity<Page<NewsResponseDto>> getAllNews(Pageable pageable) {
        return newsClient.getAllNews(pageable);
    }


    @GetMapping("/news/{newsId}/comments")
    ResponseEntity<CommentListResponseDto> getCommentsByNewsId(@PathVariable Long newsId,
                                                               Pageable pageable) {
        return newsClient.getCommentsByNewsId(newsId, pageable);
    }
}
