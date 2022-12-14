package com.example.restaurant.naver;

import com.example.restaurant.naver.dto.SearchImageReq;
import com.example.restaurant.naver.dto.SearchImageRes;
import com.example.restaurant.naver.dto.SearchLocalReq;
import com.example.restaurant.naver.dto.SearchLocalRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NaverClient {

    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClintSecret;

    @Value("${naver.url.search.local}")
    private String naverLocalUrl;

    @Value("${naver.url.search.image}")
    private String naverImageUrl;

    public SearchLocalRes localSearch(SearchLocalReq searchLocalReq) {
        URI uri = UriComponentsBuilder
                .fromUriString(naverLocalUrl)
                .queryParams(searchLocalReq.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        var header = new HttpHeaders();
        header.set("X-Naver-Client-Id", naverClientId);
        header.set("X-Naver-Client-Secret", naverClintSecret);
        header.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(header);
        var responseType = new ParameterizedTypeReference<SearchLocalRes>(){};

        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );
        return responseEntity.getBody();
    }
    public SearchImageRes imageSearch(SearchImageReq searchImageReq){

        URI uri = UriComponentsBuilder
                .fromUriString(naverImageUrl)
                .queryParams(searchImageReq.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        var header = new HttpHeaders();
        header.set("X-Naver-Client-Id", naverClientId);
        header.set("X-Naver-Client-Secret", naverClintSecret);
        header.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(header);
        var responseType = new ParameterizedTypeReference<SearchImageRes>(){};

        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );
        return responseEntity.getBody();
    }
}
