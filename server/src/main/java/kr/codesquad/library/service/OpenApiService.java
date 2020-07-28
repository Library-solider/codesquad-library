package kr.codesquad.library.service;

import kr.codesquad.library.domain.openapi.response.OpenApiItemList;
import kr.codesquad.library.domain.openapi.response.OpenApiResponseDto;
import kr.codesquad.library.global.config.properties.vo.OpenApiParameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class OpenApiService {

    private final RestTemplate restTemplate;
    private final OpenApiParameter apiParameter;

    @Transactional(readOnly = true)
    public OpenApiResponseDto requestBook(String isbn) {
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(apiParameter.getUrl())
                .queryParam("key", apiParameter.getKey())
                .queryParam("output", apiParameter.getOutput())
                .queryParam("queryType", apiParameter.getQueryType())
                .queryParam("query", isbn)
                .build(false);

        OpenApiItemList items = restTemplate.getForObject(builder.toString(), OpenApiItemList.class);
        return items.getItem().get(0);
    }
}
