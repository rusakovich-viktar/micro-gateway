package by.clevertec.gateway.client;

import static by.clevertec.gateway.util.Constant.BaseApi.COMMENTS;
import static by.clevertec.gateway.util.Constant.BaseApi.COMMENTS_ID;
import static by.clevertec.gateway.util.Constant.BaseApi.COMMENTS_NEWS_NEWS_ID;

import by.clevertec.gateway.dto.request.CommentRequestDto;
import by.clevertec.gateway.dto.response.CommentResponseDto;
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
 * Клиент для работы с микросервисом комментариев.
 */
@FeignClient(name = "micro-comments", url = "${comments-service.url}")
public interface CommentsClient {

    /**
     * Создает комментарий к новости.
     *
     * @param newsId            идентификатор новости
     * @param commentRequestDto DTO запроса на создание комментария
     * @return созданный комментарий
     */
    @PostMapping(COMMENTS_NEWS_NEWS_ID)
    ResponseEntity<CommentResponseDto> createComment(@PathVariable Long newsId,
                                                     @Valid @RequestBody CommentRequestDto commentRequestDto);

    /**
     * Получает комментарий по идентификатору.
     *
     * @param id идентификатор комментария
     * @return комментарий
     */
    @GetMapping(COMMENTS_ID)
    ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id);

    /**
     * Обновляет комментарий.
     *
     * @param id                идентификатор комментария
     * @param commentRequestDto DTO запроса на обновление комментария
     * @return обновленный комментарий
     */
    @PutMapping(COMMENTS_ID)
    ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id,
                                                     @Valid @RequestBody CommentRequestDto commentRequestDto);

    /**
     * Удаляет комментарий.
     *
     * @param id идентификатор комментария
     * @return HTTP статус 204 (No Content), если комментарий успешно удален
     */
    @DeleteMapping(COMMENTS_ID)
    ResponseEntity<Void> deleteComment(@PathVariable Long id);

    /**
     * Получает все комментарии.
     *
     * @param pageable параметры пагинации
     * @return страница с комментариями
     */
    @GetMapping(COMMENTS)
    ResponseEntity<Page<CommentResponseDto>> getAllComment(Pageable pageable);

}
