package by.clevertec.gateway.controller.api.v1;

import static by.clevertec.gateway.util.Constant.BaseApi.API_V_1;
import static by.clevertec.gateway.util.Constant.BaseApi.COMMENTS_NEWS_NEWS_ID;

import by.clevertec.gateway.client.CommentsClient;
import by.clevertec.gateway.dto.request.CommentRequestDto;
import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.util.Constant.BaseApi;
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
 * Контроллер для работы с комментариями.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(API_V_1)
public class CommentController {

    private final CommentsClient commentsClient;

    /**
     * Создает комментарий к новости.
     *
     * @param newsId            идентификатор новости
     * @param commentRequestDto DTO запроса на создание комментария
     * @return созданный комментарий
     */
    @PostMapping(COMMENTS_NEWS_NEWS_ID)
    ResponseEntity<CommentResponseDto> createComment(@PathVariable Long newsId,
                                                     @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentsClient.createComment(newsId, commentRequestDto);
    }

    /**
     * Получает комментарий по идентификатору.
     *
     * @param id идентификатор комментария
     * @return комментарий
     */
    @GetMapping(BaseApi.COMMENTS_ID)
    ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        return commentsClient.getCommentById(id);
    }

    /**
     * Обновляет комментарий.
     *
     * @param id                идентификатор комментария
     * @param commentRequestDto DTO запроса на обновление комментария
     * @return обновленный комментарий
     */
    @PutMapping(BaseApi.COMMENTS_ID)
    ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id,
                                                     @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentsClient.updateComment(id, commentRequestDto);
    }

    /**
     * Удаляет комментарий.
     *
     * @param id идентификатор комментария
     * @return HTTP статус 204 (No Content), если комментарий успешно удален
     */
    @DeleteMapping(BaseApi.COMMENTS_ID)
    ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        return commentsClient.deleteComment(id);
    }

    /**
     * Получает все комментарии.
     *
     * @param pageable параметры пагинации
     * @return страница с комментариями
     */
    @GetMapping(BaseApi.COMMENTS)
    ResponseEntity<Page<CommentResponseDto>> getAllComment(Pageable pageable) {
        return commentsClient.getAllComment(pageable);
    }

}
