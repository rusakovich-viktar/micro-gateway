package by.clevertec.gateway.controller.api.v1;

import static by.clevertec.gateway.util.TestConstant.Attributes.APPLICATION_JSON;
import static by.clevertec.gateway.util.TestConstant.Path.API_V_1_NEWS;
import static by.clevertec.gateway.util.TestConstant.Path.API_V_1_NEWS_ID;
import static by.clevertec.gateway.util.TestConstant.Path.API_V_1_NEWS_NEWS_ID_COMMENTS;
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

import by.clevertec.gateway.client.NewsClient;
import by.clevertec.gateway.dto.request.NewsRequestDto;
import by.clevertec.gateway.dto.response.CommentListResponseDto;
import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
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
@WebMvcTest(NewsController.class)
class NewsControllerTest {


    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockBean
    private NewsClient newsClient;

    @Test
    public void testCreateNews() throws Exception {
        NewsRequestDto newsRequestDto = DataTestBuilder.builder()
                .build()
                .buildNewsRequestDto();

        NewsResponseDto newsResponseDto = DataTestBuilder.builder()
                .build()
                .buildNewsResponseDto();

        when(newsClient.createNews(any(NewsRequestDto.class)))
                .thenReturn(new ResponseEntity<>(newsResponseDto, HttpStatus.OK));

        mockMvc.perform(post(API_V_1_NEWS)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(newsRequestDto.getTitle())))
                .andExpect(jsonPath("$.text", is(newsRequestDto.getText())));
    }

    @Test
    public void testGetNewsById() throws Exception {
        NewsResponseDto newsResponseDto = DataTestBuilder.builder()
                .build()
                .buildNewsResponseDto();

        when(newsClient.getNewsById(anyLong()))
                .thenReturn(new ResponseEntity<>(newsResponseDto, HttpStatus.OK));

        mockMvc.perform(get(API_V_1_NEWS_ID, 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(newsResponseDto.getTitle())))
                .andExpect(jsonPath("$.text", is(newsResponseDto.getText())));
    }

    @Test
    public void testUpdateNews() throws Exception {
        NewsRequestDto newsRequestDto = DataTestBuilder.builder()
                .build()
                .buildNewsRequestDto();

        NewsResponseDto newsResponseDto = DataTestBuilder.builder()
                .build()
                .buildNewsResponseDto();

        when(newsClient.updateNews(anyLong(), any(NewsRequestDto.class)))
                .thenReturn(new ResponseEntity<>(newsResponseDto, HttpStatus.OK));

        mockMvc.perform(put(API_V_1_NEWS_ID, 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(newsRequestDto.getTitle())))
                .andExpect(jsonPath("$.text", is(newsRequestDto.getText())));
    }

    @Test
    public void testDeleteNews() throws Exception {
        when(newsClient.deleteNews(anyLong()))
                .thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete(API_V_1_NEWS_ID, 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetAllNews() throws Exception {
        NewsResponseDto newsResponseDto = DataTestBuilder.builder()
                .build()
                .buildNewsResponseDto();

        List<NewsResponseDto> newsResponseDtoList = new ArrayList<>();
        newsResponseDtoList.add(newsResponseDto);

        Page<NewsResponseDto> newsResponseDtoPage = new PageImpl<>(newsResponseDtoList);

        when(newsClient.getAllNews(any(Pageable.class)))
                .thenReturn(new ResponseEntity<>(newsResponseDtoPage, HttpStatus.OK));

        mockMvc.perform(get(API_V_1_NEWS)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCommentsByNewsId() throws Exception {
        CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                .build()
                .buildCommentResponseDto();

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        commentResponseDtoList.add(commentResponseDto);

        CommentListResponseDto commentListResponseDto = new CommentListResponseDto(commentResponseDtoList);

        when(newsClient.getCommentsByNewsId(anyLong(), any(Pageable.class)))
                .thenReturn(new ResponseEntity<>(commentListResponseDto, HttpStatus.OK));

        mockMvc.perform(get(API_V_1_NEWS_NEWS_ID_COMMENTS, 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}