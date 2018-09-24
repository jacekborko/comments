package com.jb.comments.controller;

import com.jb.comments.CommentsApplication;
import lombok.Getter;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommentsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class PostControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Getter
    protected RestTemplate restTemplate;

    @Before
    public void setUp() {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(12000)
                .setReadTimeout(12000)
                .build();
    }


    protected String getUrl(String uri) {
        return "http://localhost:" + port + uri;
    }
}