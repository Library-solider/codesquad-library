package com.librarycodesquad.prod.domain.openapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OpenApiBookDataResponse {

    @JsonProperty("description")
    private String description;

    @JsonProperty("coverLargeUrl")
    private String imageUrl;
}
