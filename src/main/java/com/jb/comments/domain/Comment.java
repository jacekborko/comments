package com.jb.comments.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Objects;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    private Long id;
    private Long postId;
    private String name;
    private Email email;
    private String body;

    public boolean hasEmail(){
        return Objects.nonNull(email);
    }

    public String getEmailDomain(){
        return email.getDomain();
    }
}
