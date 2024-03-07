package by.clevertec.gateway.client;

import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import by.clevertec.gateway.util.Constant.BaseApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Клиент для работы с микросервисом поиска.
 */
@FeignClient(name = "micro-search", url = "${search-service.url}")
public interface SearchClient {

    /**
     * Поиск новостей по тексту.
     *
     * @param text     текст для поиска
     * @param pageable параметры пагинации
     * @return страница с результатами поиска новостей
     */
    @GetMapping(BaseApi.SEARCH_NEWS)
    ResponseEntity<Page<NewsResponseDto>> searchNews(@RequestParam String text, Pageable pageable);

    /**
     * Поиск комментариев по тексту.
     *
     * @param text     текст для поиска
     * @param pageable параметры пагинации
     * @return страница с результатами поиска комментариев
     */
    @GetMapping(BaseApi.SEARCH_COMMENTS)
    ResponseEntity<Page<CommentResponseDto>> searchComments(@RequestParam String text, Pageable pageable);
}
