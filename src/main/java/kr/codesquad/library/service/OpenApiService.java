package kr.codesquad.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@RequiredArgsConstructor
@Service
public class OpenApiService {

    private final RestTemplate restTemplate;
}
