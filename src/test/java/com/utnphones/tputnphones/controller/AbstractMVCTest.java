package com.utnphones.tputnphones.controller;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@ContextConfiguration
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public abstract class AbstractMVCTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    protected MockMvc givenController() {
        return MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .build();
    }
}
