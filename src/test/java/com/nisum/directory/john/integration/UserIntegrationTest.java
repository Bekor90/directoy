package com.nisum.directory.john.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        String payload = """
            {
              "name": "Test User",
              "email": "test@domain.cl",
              "password": "Abc123",
              "phones": [
                {
                  "number": "1234567",
                  "citycode": "1",
                  "contrycode": "57"
                }
              ]
            }
        """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void shouldRegisterUserPasswordFormatIncorrectResponseBadRequest() throws Exception {
        String payload = """
            {
              "name": "Test User",
              "email": "test@correo.cl",
              "password": "clave123",
              "phones": [
                {
                  "number": "1234567",
                  "citycode": "1",
                  "contrycode": "57"
                }
              ]
            }
        """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRegisterUserEmailFormatIncorrectResponseBadRequest() throws Exception {
        String payload = """
            {
              "name": "Test User",
              "email": "test@correo.co",
              "password": "Abc123",
              "phones": [
                {
                  "number": "1234567",
                  "citycode": "1",
                  "contrycode": "57"
                }
              ]
            }
        """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRegisterUserEmailNullResponseBadRequest() throws Exception {
        String payload = """
            {
              "name": "Test User",
              "password": "Abc123",
              "phones": [
                {
                  "number": "1234567",
                  "citycode": "1",
                  "contrycode": "57"
                }
              ]
            }
        """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRegisterUserPasswordNullResponseBadRequest() throws Exception {
        String payload = """
            {
              "name": "Test User",
              "email": "test@correo.cl",
              "phones": [
                {
                  "number": "1234567",
                  "citycode": "1",
                  "contrycode": "57"
                }
              ]
            }
        """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRegisterUserPhoneNumberResponseBadRequest() throws Exception {
        String payload = """
            {
              "name": "Test User",
              "email": "test@correo.cl",
              "phones": [
                {
                  "citycode": "1",
                  "contrycode": "57"
                }
              ]
            }
        """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRegisterUserCityCodeResponseBadRequest() throws Exception {
        String payload = """
            {
              "name": "Test User",
              "email": "test@correo.cl",
              "phones": [
                {
                 "number": "1234567",
                  "contrycode": "57"
                }
              ]
            }
        """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRegisterUserContryCodeResponseBadRequest() throws Exception {
        String payload = """
            {
              "name": "Test User",
              "email": "test@correo.cl",
              "password": "Abc123",
              "phones": [
                {
                  "number": "1234567",
                  "citycode": "1"
                }
              ]
            }
        """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }
}

