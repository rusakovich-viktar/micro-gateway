package by.clevertec.gateway.controller.api.v1;

import by.clevertec.gateway.client.CommentsClient;
import by.clevertec.gateway.dto.request.CommentRequestDto;
import by.clevertec.gateway.dto.response.CommentResponseDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentsClient commentsClient;

    @PostMapping("/comments/news/{newsId}")
    ResponseEntity<CommentResponseDto> createComment(@PathVariable Long newsId,
                                                     @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentsClient.createComment(newsId, commentRequestDto);
    }

    @GetMapping("/comments/{id}")
    ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long id) {
        return commentsClient.getCommentById(id);
    }


    @PutMapping("/comments/{id}")
    ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id,
                                                     @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentsClient.updateComment(id, commentRequestDto);
    }

    @DeleteMapping("/comments/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        return commentsClient.deleteComment(id);
    }


    @GetMapping("/comments")
    ResponseEntity<Page<CommentResponseDto>> getAllComment(Pageable pageable) {
        return commentsClient.getAllComment(pageable);
    }

}



