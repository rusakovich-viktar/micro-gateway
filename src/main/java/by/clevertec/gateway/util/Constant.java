package by.clevertec.gateway.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

    @UtilityClass
    public class BaseApi {

        public static final String API_V_1 = "/api/v1";
        public static final String HTTP_LOCALHOST_8081 = "http://localhost:8081";
        public static final String HTTP_LOCALHOST_8082 = "http://localhost:8082";
        public static final String HTTP_LOCALHOST_8083 = "http://localhost:8083";
        public static final String COMMENTS = "/comments";
        public static final String COMMENTS_ID = "/comments/{id}";
        public static final String COMMENTS_SEARCH = "/comments/search";
        public static final String COMMENTS_NEWS_NEWS_ID = "/comments/news/{newsId}";
        public static final String NEWS = "/news";
        public static final String NEWS_ID = "/news/{id}";
        public static final String NEWS_SEARCH = "/news/search";
        public static final String NEWS_NEWS_ID_COMMENTS = "/news/{newsId}/comments";
        public static final String SEARCH_NEWS = "/search/news";
        public static final String SEARCH_COMMENTS = "/search/comments";
    }

    @UtilityClass
    public class Messages {

        public static final String TEXT_CANNOT_BE_BLANK = "Text cannot be blank";
        public static final String TITLE_CANNOT_BE_BLANK = "Title cannot be blank";
        public static final String TEXT_SHOULD_BE_MINIMUM_5_SYMBOLS = "Text should be minimum 5 symbols";
    }

}
