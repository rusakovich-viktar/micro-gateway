package by.clevertec.gateway.client;

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

@FeignClient(name = "micro-comments", url = "http://localhost:8082")
public interface CommentsClient {

    @PostMapping("/comments/news/{newsId}")
    ResponseEntity<CommentResponseDto> createComment(@PathVariable Long newsId,
                                                     @Valid @RequestBody CommentRequestDto commentRequestDto);

    @GetMapping("/comments/{id}")
    ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id);


    @PutMapping("/comments/{id}")
    ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id,
                                                     @Valid @RequestBody CommentRequestDto commentRequestDto);

    @DeleteMapping("/comments/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable Long id);


    @GetMapping("/comments")
    ResponseEntity<Page<CommentResponseDto>> getAllComment(Pageable pageable);


    @DeleteMapping("/comments/news/{newsId}")
    ResponseEntity<Void> deleteCommentsByNewsId(@PathVariable Long newsId);


    @GetMapping("/comments/news/{newsId}")
    ResponseEntity<Page<CommentResponseDto>> getCommentsByNewsId(@PathVariable Long newsId, Pageable pageable);


}


