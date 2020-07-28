package kr.codesquad.library.service;

import kr.codesquad.library.domain.openapi.response.OpenApiBookDataResponse;
import kr.codesquad.library.domain.openapi.response.OpenApiResponse;
import kr.codesquad.library.global.config.properties.vo.OpenApiParameter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class OpenApiService {

    private final RestTemplate restTemplate;
    private final OpenApiParameter apiParameter;

    @Transactional(readOnly = true)
    public OpenApiBookDataResponse requestBookData(String isbn) {
        String requestUrl = UriComponentsBuilder.fromHttpUrl(apiParameter.getUrl())
                                                .queryParam("key", apiParameter.getKey())
                                                .queryParam("output", apiParameter.getOutput())
                                                .queryParam("queryType", apiParameter.getQueryType())
                                                .queryParam("query", isbn)
                                                .build(false)
                                                .toString();

        OpenApiResponse items = restTemplate.getForObject(requestUrl, OpenApiResponse.class);
        return items.getItem().get(0);
    }
}
