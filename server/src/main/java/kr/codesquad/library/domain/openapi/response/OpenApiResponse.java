package kr.codesquad.library.domain.openapi.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OpenApiResponse {

    private final List<OpenApiBookDataResponse> item = new ArrayList<>();
}
