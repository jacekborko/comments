package com.jb.comments.controller;

import com.jb.comments.error.ErrorType;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpServerErrorException;

import static org.junit.Assert.assertEquals;

@TestPropertySource(properties = "posts.service.path = http://www.google.pl")
public class PostControllerWrongServiceTest extends PostControllerTest {

    @Test
    public void shouldReturnThatThereIsAProblemWithConfiguration(){
        try {
            getRestTemplate().getForEntity(getUrl("/v1/fetchComments/1"), ErrorType.class);
        } catch (HttpServerErrorException exception){
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
            assertEquals(ErrorType.SERVICE_CONNECTION_ERROR.getMessage(), exception.getResponseBodyAsString());
        }
    }
}
