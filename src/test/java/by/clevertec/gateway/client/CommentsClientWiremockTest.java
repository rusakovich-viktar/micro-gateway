package by.clevertec.gateway.client;

import static by.clevertec.gateway.util.TestConstant.ID_ONE;
import static by.clevertec.gateway.util.TestConstant.Path.COMMENTS;
import static by.clevertec.gateway.util.TestConstant.Path.COMMENTS_NEWS;
import static by.clevertec.gateway.util.TestConstant.Path.COMMENTS_SIZE_5_PAGE_0;
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

import by.clevertec.gateway.dto.request.CommentRequestDto;
import by.clevertec.gateway.dto.response.CommentResponseDto;
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
@WireMockTest(httpPort = 8082)
class CommentsClientWiremockTest {

    private final CommentsClient commentsClient;

    private ObjectMapper objectMapper;
    private final Long id = ID_ONE;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateComment() throws JsonProcessingException {
        Long newsId = id;
        CommentRequestDto commentRequestDto = DataTestBuilder.builder()
                .build()
                .buildCommentRequestDto();

        CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                .build()
                .buildCommentResponseDto();

        stubFor(post(urlEqualTo(COMMENTS_NEWS + newsId))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(commentRequestDto)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(commentResponseDto))));

        ResponseEntity<CommentResponseDto> responseEntity = commentsClient.createComment(newsId, commentRequestDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commentResponseDto, responseEntity.getBody());
    }

    @Test
    public void testGetCommentById() throws JsonProcessingException {

        CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                .build()
                .buildCommentResponseDto();

        stubFor(get(urlEqualTo(COMMENTS + id))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(commentResponseDto))));

        ResponseEntity<CommentResponseDto> responseEntity = commentsClient.getCommentById(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commentResponseDto, responseEntity.getBody());
    }

    @Test
    public void testUpdateComment() throws JsonProcessingException {

        CommentRequestDto commentRequestDto = DataTestBuilder.builder()
                .build()
                .buildCommentRequestDto();

        CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                .build()
                .buildCommentResponseDto();

        stubFor(put(urlEqualTo(COMMENTS + id))
                .withRequestBody(equalToJson(objectMapper.writeValueAsString(commentRequestDto)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(commentResponseDto))));

        ResponseEntity<CommentResponseDto> responseEntity = commentsClient.updateComment(id, commentRequestDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commentResponseDto, responseEntity.getBody());
    }

    @Test
    public void testDeleteComment() {

        stubFor(delete(urlEqualTo(COMMENTS + id))
                .willReturn(aResponse()
                        .withStatus(200)));

        ResponseEntity<Void> responseEntity = commentsClient.deleteComment(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetAllComment() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 5);

        List<CommentResponseDto> commentList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CommentResponseDto commentResponseDto = DataTestBuilder.builder()
                    .build()
                    .buildCommentResponseDto();
            commentList.add(commentResponseDto);
        }

        Page<CommentResponseDto> commentPage = new PageImpl<>(commentList, pageable, commentList.size());

        stubFor(get(urlEqualTo(COMMENTS_SIZE_5_PAGE_0))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(objectMapper.writeValueAsString(commentPage))));

        ResponseEntity<Page<CommentResponseDto>> responseEntity = commentsClient.getAllComment(pageable);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(commentList.size(), responseEntity.getBody().getContent().size());
    }
}
