package kr.codesquad.library.domain.openapi.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OpenApiItemList {

    private final List<OpenApiResponseDto> item = new ArrayList<>();
}
