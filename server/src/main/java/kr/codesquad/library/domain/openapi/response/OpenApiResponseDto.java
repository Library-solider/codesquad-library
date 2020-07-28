package kr.codesquad.library.domain.openapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OpenApiResponseDto {

    @JsonProperty("description")
    private String description;

    @JsonProperty("coverLargeUrl")
    private String imageUrl;
}
