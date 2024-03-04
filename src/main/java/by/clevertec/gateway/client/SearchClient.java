package by.clevertec.gateway.client;

import by.clevertec.gateway.dto.response.CommentResponseDto;
import by.clevertec.gateway.dto.response.NewsResponseDto;
import by.clevertec.gateway.util.Constant.BaseApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "micro-search", url = "${search-service.url}")
public interface SearchClient {

    @GetMapping(BaseApi.SEARCH_NEWS)
    ResponseEntity<Page<NewsResponseDto>> searchNews(@RequestParam String text, Pageable pageable);

    @GetMapping(BaseApi.SEARCH_COMMENTS)
    ResponseEntity<Page<CommentResponseDto>> searchComments(@RequestParam String text, Pageable pageable);
}
