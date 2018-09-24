package com.jb.comments.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailTest {

    @Test
    public void createFromString(){
        Email email = new Email("address@gmail.com");

        assertEquals("address", email.getId());
        assertEquals("gmail.com", email.getDomain());
        assertEquals("address@gmail.com", email.toString());
    }

    @Test
    public void doNotCreateFromNull(){
        Email email = new Email(null);

        assertNull(email.getId());
        assertNull(email.getDomain());
    }

    @Test
    public void doNotCreateFromEmptyString(){
        Email email = new Email("");

        assertNull(email.getId());
        assertNull(email.getDomain());
    }

}