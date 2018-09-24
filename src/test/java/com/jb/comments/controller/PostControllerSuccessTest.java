package com.jb.comments.controller;


import com.jb.comments.error.ErrorType;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class PostControllerSuccessTest extends PostControllerTest {

    @Test
    public void shouldReturnSuccess() {
        ResponseEntity result =
                getRestTemplate().getForEntity(getUrl("/v1/fetchComments/1"), ErrorType.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
