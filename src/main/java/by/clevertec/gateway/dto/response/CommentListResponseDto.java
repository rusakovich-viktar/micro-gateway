package by.clevertec.gateway.dto.response;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Класс для передачи данных о списке комментариев.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponseDto implements Serializable {

    private List<CommentResponseDto> comments;

}
