package by.clevertec.gateway.client;

import static by.clevertec.gateway.util.TestConstant.ID_ONE;
import static by.clevertec.gateway.util.TestConstant.Path.COMMENTS_SIZE_5_PAGE_0;
import static by.clevertec.gateway.util.TestConstant.Path.NEWS;
import static by.clevertec.gateway.util.TestConstant.Path.NEWS1;
import static by.clevertec.gateway.util.TestConstant.Path.NEWS_SIZE_5_PAGE_0;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON;
import static com.github.tomakehurst.wiremock.common.ContentTypes.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import by.clevertec.gateway.dto.request.NewsRequestDto;
import by.clevertec.gateway.dto.response.CommentListResponseDto;
import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import by.clevertec.gateway.util.DataTestBuilder;
import by.clevertec.gateway.util.TestConstant;
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
@WireMockTest(httpPort = 8081)
class NewsClientWiremockTest {


    private final NewsClient newsClient;

    private ObjectMapper objectMapper;

    private final Long id = ID_ONE;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateNews() throws JsonProcessingException {
        NewsRequestDto newsRequestDto = DataTestBuilder.builder()
                .build()
                .buildNewsRequestDto();

        NewsResponseDto newsResponseDto = DataTestBuilder.builder()
                .build()
                .buildNewsResponseDto();


        stubFor(post(urlEqualTo(NEWS))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(newsRequestDto)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(newsResponseDto))));

        ResponseEntity<NewsResponseDto> responseEntity = newsClient.createNews(newsRequestDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newsResponseDto, responseEntity.getBody());
    }

    @Test
    public void testGetNewsById() throws JsonProcessingException {
        NewsResponseDto newsResponseDto = DataTestBuilder.builder()
                .build()
                .buildNewsResponseDto();


        stubFor(get(urlEqualTo(NEWS1 + id))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(newsResponseDto))));

        ResponseEntity<NewsResponseDto> responseEntity = newsClient.getNewsById(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newsResponseDto, responseEntity.getBody());
    }

    @Test
    public void testUpdateNews() throws JsonProcessingException {
        NewsRequestDto newsRequestDto = DataTestBuilder.builder()
                .build()
                .buildNewsRequestDto();

        NewsResponseDto newsResponseDto = DataTestBuilder.builder()
                .build()
                .buildNewsResponseDto();


        stubFor(put(urlEqualTo(NEWS1 + id))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(newsRequestDto)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(newsResponseDto))));

        ResponseEntity<NewsResponseDto> responseEntity = newsClient.updateNews(id, newsRequestDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newsResponseDto, responseEntity.getBody());
    }

    @Test
    public void testGetAllNews() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 5);

        List<NewsResponseDto> newsList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NewsResponseDto newsResponseDto = DataTestBuilder.builder()
                    .build()
                    .buildNewsResponseDto();
            newsList.add(newsResponseDto);
        }

        Page<NewsResponseDto> newsPage = new PageImpl<>(newsList, pageable, newsList.size());


        stubFor(get(urlEqualTo(NEWS_SIZE_5_PAGE_0))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(newsPage))));

        ResponseEntity<Page<NewsResponseDto>> responseEntity = newsClient.getAllNews(pageable);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newsList.size(), responseEntity.getBody().getContent().size());
    }

    @Test
    public void testGetCommentsByNewsId() throws JsonProcessingException {
        Long newsId = id;
        Pageable pageable = PageRequest.of(0, 5);

        List<CommentResponseDto> commentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                    .build()
                    .buildCommentResponseDto();
            commentList.add(commentResponseDto);
        }

        CommentListResponseDto commentListResponseDto = new CommentListResponseDto(commentList);


        stubFor(get(urlEqualTo(NEWS1 + newsId + COMMENTS_SIZE_5_PAGE_0))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(commentListResponseDto))));

        ResponseEntity<CommentListResponseDto> responseEntity = newsClient.getCommentsByNewsId(newsId, pageable);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commentList.size(), responseEntity.getBody().getComments().size());
    }

    @Test
    public void testDeleteNews() {
        stubFor(delete(urlEqualTo(NEWS1 + id))
                .willReturn(aResponse()
                        .withStatus(200)));

        ResponseEntity<Void> responseEntity = newsClient.deleteNews(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


}