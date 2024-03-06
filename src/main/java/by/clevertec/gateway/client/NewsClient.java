package by.clevertec.gateway.client;

import static by.clevertec.gateway.util.Constant.BaseApi.NEWS;
import static by.clevertec.gateway.util.Constant.BaseApi.NEWS_ID;

import by.clevertec.gateway.dto.request.NewsRequestDto;
import by.clevertec.gateway.dto.response.CommentListResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import by.clevertec.gateway.util.Constant.BaseApi;
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

/**
 * Клиент для работы с микросервисом новостей.
 */
@FeignClient(name = "micro-news", url = "${news-service.url}")
public interface NewsClient {

    /**
     * Создает новость.
     *
     * @param newsRequestDto DTO запроса на создание новости
     * @return созданная новость
     */
    @PostMapping(NEWS)
    ResponseEntity<NewsResponseDto> createNews(@Valid @RequestBody NewsRequestDto newsRequestDto);

    /**
     * Получает новость по идентификатору.
     *
     * @param id идентификатор новости
     * @return новость
     */
    @GetMapping(NEWS_ID)
    ResponseEntity<NewsResponseDto> getNewsById(@PathVariable Long id);

    /**
     * Обновляет новость.
     *
     * @param id             идентификатор новости
     * @param newsRequestDto DTO запроса на обновление новости
     * @return обновленная новость
     */
    @PutMapping(NEWS_ID)
    ResponseEntity<NewsResponseDto> updateNews(@PathVariable Long id,
                                               @Valid @RequestBody NewsRequestDto newsRequestDto);

    /**
     * Удаляет новость.
     *
     * @param id идентификатор новости
     * @return HTTP статус 204 (No Content), если новость успешно удалена
     */
    @DeleteMapping(NEWS_ID)
    ResponseEntity<Void> deleteNews(@PathVariable Long id);

    /**
     * Получает все новости.
     *
     * @param pageable параметры пагинации
     * @return страница с новостями
     */
    @GetMapping(NEWS)
    ResponseEntity<Page<NewsResponseDto>> getAllNews(Pageable pageable);

    /**
     * Получает все комментарии к новости.
     *
     * @param newsId   идентификатор новости
     * @param pageable параметры пагинации
     * @return страница с комментариями к новости
     */
    @GetMapping(BaseApi.NEWS_NEWS_ID_COMMENTS)
    ResponseEntity<CommentListResponseDto> getCommentsByNewsId(@PathVariable Long newsId,
                                                               Pageable pageable);
}
