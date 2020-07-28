package kr.codesquad.library.global.config.properties.vo;

import lombok.Getter;

@Getter
public class OpenApiParameter {

    private final String url;
    private final String key;
    private final String output;
    private final String queryType;

    public OpenApiParameter(String url, String key, String output, String queryType) {
        this.url = url;
        this.key = key;
        this.output = output;
        this.queryType = queryType;
    }
}
