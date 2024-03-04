package by.clevertec.gateway.client;

import by.clevertec.gateway.dto.request.NewsRequestDto;
import by.clevertec.gateway.dto.response.CommentListResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import by.clevertec.gateway.util.Constant.BaseApi;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "micro-news", url = "${news-service.url}")
public interface NewsClient {

    @PostMapping(BaseApi.NEWS)
    ResponseEntity<NewsResponseDto> createNews(@Valid @RequestBody NewsRequestDto newsRequestDto);

    @GetMapping(BaseApi.NEWS_ID)
    ResponseEntity<NewsResponseDto> getNewsById(@PathVariable Long id);

    @PutMapping(BaseApi.NEWS_ID)
    ResponseEntity<NewsResponseDto> updateNews(@PathVariable Long id,
                                               @Valid @RequestBody NewsRequestDto newsRequestDto);

    @DeleteMapping(BaseApi.NEWS_ID)
    ResponseEntity<Void> deleteNews(@PathVariable Long id);

    @GetMapping(BaseApi.NEWS)
    ResponseEntity<Page<NewsResponseDto>> getAllNews(Pageable pageable);

    @GetMapping(BaseApi.NEWS_NEWS_ID_COMMENTS)
    ResponseEntity<CommentListResponseDto> getCommentsByNewsId(@PathVariable Long newsId,
                                                               Pageable pageable);
}
