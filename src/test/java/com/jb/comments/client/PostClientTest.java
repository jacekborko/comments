package com.jb.comments.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jb.comments.domain.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(PostClient.class)
public class PostClientTest {


    @Autowired
    private PostClient postClient;

    @Autowired
    private MockRestServiceServer server;


    @Autowired
    private ObjectMapper objectMapper;


    private int postSize = 2;
    private String fullPath;

    @Before
    public void setUp() throws Exception {
        String detailsString = objectMapper.writeValueAsString(new Post[]{new Post(), new Post()});
        this.fullPath = postClient.getPath() + postSize;
        this.server.expect(requestTo(fullPath))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldReturnPosts() {
        List<Post> posts = this.postClient.getPosts(postSize);
        assertEquals(postSize,posts.size());
    }

    @Test
    public void shouldReturnDataFromExternalServer() {
        ResponseEntity<Post[]> response = this.postClient.getRestTemplate().getForEntity(fullPath, Post[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(postSize, response.getBody().length);
    }

}