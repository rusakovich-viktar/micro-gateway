package by.clevertec.gateway.client;

import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "micro-search", url = "http://localhost:8083")
public interface SearchClient {

    @GetMapping("/search/news")
    ResponseEntity<Page<NewsResponseDto>> searchNews(@RequestParam String text, Pageable pageable);

    @GetMapping("/search/comments")
    ResponseEntity<Page<CommentResponseDto>> _searchComments(@RequestParam String text, Pageable pageable);
}




