package by.clevertec.gateway.client;

import by.clevertec.gateway.dto.request.NewsRequestDto;
import by.clevertec.gateway.dto.response.CommentListResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "micro-news", url = "http://localhost:8081")
public interface NewsClient {

    @PostMapping("/news")
    ResponseEntity<NewsResponseDto> createNews(@Valid @RequestBody NewsRequestDto newsRequestDto);


    @GetMapping("/news/{id}")
    ResponseEntity<NewsResponseDto> getNewsById(@PathVariable Long id);


    @PutMapping("/news/{id}")
    ResponseEntity<NewsResponseDto> updateNews(@PathVariable Long id,
                                               @Valid @RequestBody NewsRequestDto newsRequestDto);


    @DeleteMapping("/news/{id}")
    ResponseEntity<Void> deleteNews(@PathVariable Long id);


    @GetMapping("/news")
    ResponseEntity<Page<NewsResponseDto>> getAllNews(Pageable pageable);


    @GetMapping("/news/{newsId}/comments")
    ResponseEntity<CommentListResponseDto> getCommentsByNewsId(@PathVariable Long newsId,
                                                               Pageable pageable);
}


