package by.clevertec.gateway.controller.api.v1;

import static by.clevertec.gateway.util.Constant.BaseApi.API_V_1;
import static by.clevertec.gateway.util.Constant.BaseApi.NEWS;
import static by.clevertec.gateway.util.Constant.BaseApi.NEWS_ID;
import static by.clevertec.gateway.util.Constant.BaseApi.NEWS_NEWS_ID_COMMENTS;

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

/**
 * Контроллер для работы с новостями.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(API_V_1)
public class NewsController {

    private final NewsClient newsClient;

    /**
     * Создает новость.
     *
     * @param newsRequestDto DTO запроса на создание новости
     * @return созданная новость
     */
    @PostMapping(NEWS)
    ResponseEntity<NewsResponseDto> createNews(@Valid @RequestBody NewsRequestDto newsRequestDto) {
        return newsClient.createNews(newsRequestDto);
    }

    /**
     * Получает новость по идентификатору.
     *
     * @param id идентификатор новости
     * @return новость
     */
    @GetMapping(NEWS_ID)
    ResponseEntity<NewsResponseDto> getNewsById(@PathVariable Long id) {
        return newsClient.getNewsById(id);
    }

    /**
     * Обновляет новость.
     *
     * @param id             идентификатор новости
     * @param newsRequestDto DTO запроса на обновление новости
     * @return обновленная новость
     */
    @PutMapping(NEWS_ID)
    ResponseEntity<NewsResponseDto> updateNews(@PathVariable Long id,
                                               @Valid @RequestBody NewsRequestDto newsRequestDto) {
        return newsClient.updateNews(id, newsRequestDto);
    }

    /**
     * Удаляет новость.
     *
     * @param id идентификатор новости
     * @return HTTP статус 204 (No Content), если новость успешно удалена
     */
    @DeleteMapping(NEWS_ID)
    ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsClient.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Получает все новости.
     *
     * @param pageable параметры пагинации
     * @return страница с новостями
     */
    @GetMapping(NEWS)
    ResponseEntity<Page<NewsResponseDto>> getAllNews(Pageable pageable) {
        return newsClient.getAllNews(pageable);
    }

    /**
     * Получает все комментарии к новости.
     *
     * @param newsId   идентификатор новости
     * @param pageable параметры пагинации
     * @return страница с комментариями к новости
     */
    @GetMapping(NEWS_NEWS_ID_COMMENTS)
    ResponseEntity<CommentListResponseDto> getCommentsByNewsId(@PathVariable Long newsId,
                                                               Pageable pageable) {
        return newsClient.getCommentsByNewsId(newsId, pageable);
    }
}
