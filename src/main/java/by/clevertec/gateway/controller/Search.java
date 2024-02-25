package by.clevertec.gateway.controller;

import by.clevertec.gateway.client.SearchClient;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Search {

    private final SearchClient searchClient;

    @GetMapping("/search/news")
    public ResponseEntity<Page<NewsResponseDto>> searchNews(@RequestParam String text, Pageable pageable) {

        return searchClient.searchNews(text, pageable);

    }
}

