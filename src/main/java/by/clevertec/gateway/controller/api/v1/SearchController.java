package by.clevertec.gateway.controller.api.v1;

import static by.clevertec.gateway.util.Constant.BaseApi.API_V_1;
import static by.clevertec.gateway.util.Constant.BaseApi.COMMENTS_SEARCH;
import static by.clevertec.gateway.util.Constant.BaseApi.NEWS_SEARCH;

import by.clevertec.gateway.client.SearchClient;
import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для поиска новостей и комментариев.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(API_V_1)
public class SearchController {

    private final SearchClient searchClient;

    /**
     * Поиск новостей по тексту.
     *
     * @param text     текст для поиска
     * @param pageable параметры пагинации
     * @return страница с результатами поиска новостей
     */
    @GetMapping(NEWS_SEARCH)
    public ResponseEntity<Page<NewsResponseDto>> searchNews(@RequestParam String text, Pageable pageable) {

        return searchClient.searchNews(text, pageable);

    }

    /**
     * Поиск комментариев по тексту.
     *
     * @param text     текст для поиска
     * @param pageable параметры пагинации
     * @return страница с результатами поиска комментариев
     */
    @GetMapping(COMMENTS_SEARCH)
    public ResponseEntity<Page<CommentResponseDto>> searchComments(@RequestParam String text, Pageable pageable) {

        return searchClient.searchComments(text, pageable);

    }

}
