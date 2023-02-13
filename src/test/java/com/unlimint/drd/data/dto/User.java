package com.unlimint.drd.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlimint.drd.helpers.JacksonMapper;
import lombok.Getter;
import lombok.Setter;

import static org.apache.logging.log4j.LogManager.getLogger;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("emailPassword")
    private String emailPassword;

    @JsonProperty("dbDatabase")
    private String dbDatabase;

    @JsonProperty("dbUser")
    private String dbUser;

    @JsonProperty("dbPassword")
    private String dbPassword;

    @JsonProperty("vaultSecretToken")
    private String vaultSecretToken;

    @JsonProperty("jenkinsUser")
    private String jenkinsUser;

    @JsonProperty("jenkinsToken")
    private String jenkinsToken;

    public User createCopy() {
        try {
            ObjectMapper jsonMapper = new JacksonMapper().getJsonMapper();
            return jsonMapper.readValue(jsonMapper.writeValueAsString(this), User.class);
        } catch (JsonProcessingException e) {
            getLogger(User.class).error(e.getMessage());
        }

        return new User();
    }
}
