package com.jb.comments.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;


@Data
@JsonSerialize(using = ToStringSerializer.class)
public class Email {

    private String id;
    private String domain;

    public Email(String data){
        Optional.ofNullable(data)
                .map(value -> value.split("@"))
                .filter(array -> array.length==2)
                .ifPresent(array -> {
                    id =  array[0];
                    domain =  array[1];
                });
    }

    @Override
    public String toString() {
        return Objects.nonNull(id) && Objects.nonNull(domain) ? String.format("%s@%s",id,domain) : null;
    }
}
