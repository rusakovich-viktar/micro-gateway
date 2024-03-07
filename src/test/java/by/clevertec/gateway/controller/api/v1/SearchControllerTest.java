package by.clevertec.gateway.controller.api.v1;

import static by.clevertec.gateway.util.TestConstant.Attributes.TEST;
import static by.clevertec.gateway.util.TestConstant.Attributes.TEXT;
import static by.clevertec.gateway.util.TestConstant.Path.API_V_1_COMMENTS_SEARCH;
import static by.clevertec.gateway.util.TestConstant.Path.API_V_1_NEWS_SEARCH;
import static com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import by.clevertec.gateway.client.SearchClient;
import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import by.clevertec.gateway.util.DataTestBuilder;
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
@WebMvcTest(SearchController.class)
class SearchControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private SearchClient searchClient;

    @Test
    public void testSearchNews() throws Exception {
        NewsResponseDto newsResponseDto = DataTestBuilder.builder()
                .build()
                .buildNewsResponseDto();

        List<NewsResponseDto> newsResponseDtoList = new ArrayList<>();
        newsResponseDtoList.add(newsResponseDto);

        Page<NewsResponseDto> newsResponseDtoPage = new PageImpl<>(newsResponseDtoList);

        when(searchClient.searchNews(anyString(), any(Pageable.class)))
                .thenReturn(new ResponseEntity<>(newsResponseDtoPage, HttpStatus.OK));

        mockMvc.perform(get(API_V_1_NEWS_SEARCH)
                        .param(TEXT, TEST)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchComments() throws Exception {
        CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                .build()
                .buildCommentResponseDto();

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        commentResponseDtoList.add(commentResponseDto);

        Page<CommentResponseDto> commentResponseDtoPage = new PageImpl<>(commentResponseDtoList);

        when(searchClient.searchComments(anyString(), any(Pageable.class)))
                .thenReturn(new ResponseEntity<>(commentResponseDtoPage, HttpStatus.OK));

        mockMvc.perform(get(API_V_1_COMMENTS_SEARCH)
                        .param(TEXT, TEST)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
