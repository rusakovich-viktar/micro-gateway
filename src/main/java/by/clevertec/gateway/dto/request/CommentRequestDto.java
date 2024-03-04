package by.clevertec.gateway.dto.request;

import by.clevertec.gateway.util.Constant.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto implements Serializable {

    @NotBlank(message = Messages.TEXT_CANNOT_BE_BLANK)
    @Size(min = 5, message = Messages.TEXT_SHOULD_BE_MINIMUM_5_SYMBOLS)
    private String text;

    private String username;

    private Long newsId;

}
