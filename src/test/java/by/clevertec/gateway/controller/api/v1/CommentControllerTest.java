package by.clevertec.gateway.controller.api.v1;

import static com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON;
import static by.clevertec.gateway.util.TestConstant.Path.API_V_1_COMMENTS;
import static by.clevertec.gateway.util.TestConstant.Path.API_V_1_COMMENTS_ID;
import static by.clevertec.gateway.util.TestConstant.Path.API_V_1_COMMENTS_NEWS_NEWS_ID;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.clevertec.gateway.client.CommentsClient;
import by.clevertec.gateway.dto.request.CommentRequestDto;
import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.util.DataTestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@RequiredArgsConstructor
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockBean
    private CommentsClient commentsClient;

    @Test
    public void testCreateComment() throws Exception {
        CommentRequestDto commentRequestDto = DataTestBuilder.builder()
                .build()
                .buildCommentRequestDto();

        CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                .build()
                .buildCommentResponseDto();

        when(commentsClient.createComment(anyLong(), any(CommentRequestDto.class)))
                .thenReturn(new ResponseEntity<>(commentResponseDto, HttpStatus.OK));

        mockMvc.perform(post(API_V_1_COMMENTS_NEWS_NEWS_ID, 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(commentRequestDto.getText())))
                .andExpect(jsonPath("$.username", is(commentRequestDto.getUsername())))
                .andExpect(jsonPath("$.newsId", is(commentRequestDto.getNewsId().intValue())));
    }

    @Test
    public void testGetCommentById() throws Exception {
        CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                .build()
                .buildCommentResponseDto();

        when(commentsClient.getCommentById(anyLong()))
                .thenReturn(new ResponseEntity<>(commentResponseDto, HttpStatus.OK));

        mockMvc.perform(get(API_V_1_COMMENTS_ID, 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(commentResponseDto.getText())))
                .andExpect(jsonPath("$.username", is(commentResponseDto.getUsername())))
                .andExpect(jsonPath("$.newsId", is(commentResponseDto.getNewsId().intValue())));
    }

    @Test
    public void testUpdateComment() throws Exception {
        CommentRequestDto commentRequestDto = DataTestBuilder.builder()
                .build()
                .buildCommentRequestDto();

        CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                .build()
                .buildCommentResponseDto();

        when(commentsClient.updateComment(anyLong(), any(CommentRequestDto.class)))
                .thenReturn(new ResponseEntity<>(commentResponseDto, HttpStatus.OK));

        mockMvc.perform(put(API_V_1_COMMENTS_ID, 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(commentRequestDto.getText())))
                .andExpect(jsonPath("$.username", is(commentRequestDto.getUsername())))
                .andExpect(jsonPath("$.newsId", is(commentRequestDto.getNewsId().intValue())));
    }

    @Test
    public void testDeleteComment() throws Exception {
        when(commentsClient.deleteComment(anyLong()))
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mockMvc.perform(delete(API_V_1_COMMENTS_ID, 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllComment() throws Exception {
        CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                .build()
                .buildCommentResponseDto();

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        commentResponseDtoList.add(commentResponseDto);

        Page<CommentResponseDto> commentResponseDtoPage = new PageImpl<>(commentResponseDtoList);

        when(commentsClient.getAllComment(any(Pageable.class)))
                .thenReturn(new ResponseEntity<>(commentResponseDtoPage, HttpStatus.OK));

        mockMvc.perform(get(API_V_1_COMMENTS)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}


