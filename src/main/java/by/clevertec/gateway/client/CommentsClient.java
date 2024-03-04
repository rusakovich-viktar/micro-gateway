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

@FeignClient(name = "micro-comments", url = "${comments-service.url}")
public interface CommentsClient {

    @PostMapping(COMMENTS_NEWS_NEWS_ID)
    ResponseEntity<CommentResponseDto> createComment(@PathVariable Long newsId,
                                                     @Valid @RequestBody CommentRequestDto commentRequestDto);

    @GetMapping(COMMENTS_ID)
    ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id);

    @PutMapping(COMMENTS_ID)
    ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id,
                                                     @Valid @RequestBody CommentRequestDto commentRequestDto);

    @DeleteMapping(COMMENTS_ID)
    ResponseEntity<Void> deleteComment(@PathVariable Long id);

    @GetMapping(COMMENTS)
    ResponseEntity<Page<CommentResponseDto>> getAllComment(Pageable pageable);

}
