package by.clevertec.gateway.util;

import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConstant {

    public static final long ID_ONE = 1L;
    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2024, 2, 25, 12, 12, 12);
    public static final String NEWS_TITLE = "Название новости";
    public static final String NEWS_TEXT = "Тут должно быть много слов по тексту новости";
    public static final String USERNAME = "Username";


    @UtilityClass
    public class Attributes {

        public static final String TEXT = "text";
        public static final String TEST = "Test";

    }

    @UtilityClass
    public class Path {
        public static final String NEWS = "/news";
        public static final String NEWS1 = "/news/";
        public static final String NEWS_SIZE_5_PAGE_0 = "/news?size=5&page=0";
        public static final String COMMENTS_SIZE_5_PAGE_0 = "/comments?size=5&page=0";
        public static final String COMMENTS_NEWS = "/comments/news/";
        public static final String COMMENTS = "/comments/";
        public static final String SEARCH_NEWS_TEXT = "/search/news?text=";
        public static final String SIZE_5_PAGE_0 = "&size=5&page=0";
        public static final String SEARCH_COMMENTS_TEXT = "/search/comments?text=";
        public static final String API_V_1_NEWS_SEARCH = "/api/v1/news/search";
        public static final String API_V_1_COMMENTS_SEARCH = "/api/v1/comments/search";
        public static final String API_V_1_COMMENTS_ID = "/api/v1/comments/{id}";
        public static final String API_V_1_COMMENTS_NEWS_NEWS_ID = "/api/v1/comments/news/{newsId}";
        public static final String API_V_1_COMMENTS = "/api/v1/comments";
        public static final String API_V_1_NEWS = "/api/v1/news";
        public static final String API_V_1_NEWS_ID = "/api/v1/news/{id}";
        public static final String API_V_1_NEWS_NEWS_ID_COMMENTS = "/api/v1/news/{newsId}/comments";
    }


}

