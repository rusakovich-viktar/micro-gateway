package by.clevertec.gateway.controller.api.v1;

import by.clevertec.gateway.client.SearchClient;
import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import by.clevertec.gateway.util.Constant.BaseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseApi.API_V_1)
public class SearchController {

    private final SearchClient searchClient;

    @GetMapping(BaseApi.NEWS_SEARCH)
    public ResponseEntity<Page<NewsResponseDto>> searchNews(@RequestParam String text, Pageable pageable) {

        return searchClient.searchNews(text, pageable);

    }

    @GetMapping(BaseApi.COMMENTS_SEARCH)
    public ResponseEntity<Page<CommentResponseDto>> searchComments(@RequestParam String text, Pageable pageable) {

        return searchClient.searchComments(text, pageable);

    }

}
