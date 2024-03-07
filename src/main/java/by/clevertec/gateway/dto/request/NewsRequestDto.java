package by.clevertec.gateway.dto.request;

import by.clevertec.gateway.util.Constant.Messages;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Transfer Object (DTO) для создания или обновления новостей.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class NewsRequestDto implements Serializable {

    @NotBlank(message = Messages.TITLE_CANNOT_BE_BLANK)
    private String title;

    @NotBlank(message = Messages.TEXT_CANNOT_BE_BLANK)
    private String text;

}
