package com.kabang.external.client;

import com.kabang.common.dto.GetV2SearchKakaoBlogDto;
import com.kabang.common.dto.request.BlogSearchRequest;
import com.kabang.common.dto.GetV1SearchNaverBlogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class ExternalApiClient {

    @Value("${api.external.kakao.path.blog}")
    private String kakaoApiBlogPath;
    @Value("${api.external.naver.path.blog}")
    private String naverApiBlogPath;

    private final RestTemplate kakaoAuthRestTemplate;
    private final RestTemplate naverAuthRestTemplate;

    public ResponseEntity<GetV2SearchKakaoBlogDto> getV2SearchKakaoBlog(BlogSearchRequest request){
        var uri = UriComponentsBuilder
                .fromPath(kakaoApiBlogPath)
                .queryParam("query", request.getKeyword())
                .queryParam("sort", request.getSort())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .build().toUriString();
        return kakaoAuthRestTemplate.getForEntity(uri, GetV2SearchKakaoBlogDto.class);
    }

    public ResponseEntity<GetV1SearchNaverBlogDto> getV1SearchNaverBlog(BlogSearchRequest request){
        var uri = UriComponentsBuilder
                .fromPath(naverApiBlogPath)
                .queryParam("query", request.getKeyword())
                .queryParam("sort", request.getSort().getNaverSortType())
                .queryParam("start", request.getPage())
                .queryParam("display", request.getSize())
                .build().toUriString();
        return naverAuthRestTemplate.getForEntity(uri, GetV1SearchNaverBlogDto.class);
    }
}
