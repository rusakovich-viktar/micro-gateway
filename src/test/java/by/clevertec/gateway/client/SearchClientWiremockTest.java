package by.clevertec.gateway.client;

import static by.clevertec.gateway.util.TestConstant.Attributes.TEST;
import static by.clevertec.gateway.util.TestConstant.Path.SEARCH_COMMENTS_TEXT;
import static by.clevertec.gateway.util.TestConstant.Path.SEARCH_NEWS_TEXT;
import static by.clevertec.gateway.util.TestConstant.Path.SIZE_5_PAGE_0;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON;
import static com.github.tomakehurst.wiremock.common.ContentTypes.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import by.clevertec.gateway.util.DataTestBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@RequiredArgsConstructor
@WireMockTest(httpPort = 8083)
class SearchClientWiremockTest {

    private final SearchClient searchClient;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testSearchNews() throws JsonProcessingException {
        String text = TEST;
        Pageable pageable = PageRequest.of(0, 5);

        List<NewsResponseDto> newsList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NewsResponseDto newsResponseDto = DataTestBuilder.builder()
                    .build()
                    .buildNewsResponseDto();
            newsList.add(newsResponseDto);
        }

        Page<NewsResponseDto> newsPage = new PageImpl<>(newsList, pageable, newsList.size());

        stubFor(get(urlEqualTo(SEARCH_NEWS_TEXT + text + SIZE_5_PAGE_0))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(newsPage))));

        ResponseEntity<Page<NewsResponseDto>> responseEntity = searchClient.searchNews(text, pageable);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newsList.size(), responseEntity.getBody().getContent().size());
    }

    @Test
    public void testSearchComments() throws JsonProcessingException {
        String text = TEST;
        Pageable pageable = PageRequest.of(0, 5);

        List<CommentResponseDto> commentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                    .build()
                    .buildCommentResponseDto();
            commentList.add(commentResponseDto);
        }

        Page<CommentResponseDto> commentPage = new PageImpl<>(commentList, pageable, commentList.size());

        stubFor(get(urlEqualTo(SEARCH_COMMENTS_TEXT + text + SIZE_5_PAGE_0))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(commentPage))));

        ResponseEntity<Page<CommentResponseDto>> responseEntity = searchClient.searchComments(text, pageable);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commentList.size(), responseEntity.getBody().getContent().size());
    }

}
