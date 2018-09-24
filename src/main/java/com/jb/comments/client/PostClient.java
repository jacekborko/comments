package com.jb.comments.client;

import com.jb.comments.domain.Post;
import com.jb.comments.error.BusinessException;
import com.jb.comments.error.ErrorType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class PostClient {

    @Getter
    private final RestTemplate restTemplate;
    @Getter
    private final String path;


    public PostClient(final RestTemplateBuilder restTemplateBuilder, @Value("${posts.service.path}") final String path) {
        this.path = path;
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(6000)
                .setReadTimeout(6000)
                .build();
    }

    public List<Post> getPosts(int size) {
        List<Post> posts;

        try{
            posts = Optional.ofNullable(restTemplate.getForObject(path + size, Post[].class))
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (RestClientException exception){
            throw new BusinessException(ErrorType.SERVICE_CONNECTION_ERROR);
        }

        return posts;
    }
}
