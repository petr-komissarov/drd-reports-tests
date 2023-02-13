package com.unlimint.drd.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import static com.fasterxml.jackson.databind.SerializationFeature.CLOSE_CLOSEABLE;
import static com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature.MINIMIZE_QUOTES;
import static com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature.WRITE_DOC_START_MARKER;

public class JacksonMapper {
    public ObjectMapper getJsonMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(CLOSE_CLOSEABLE);

        return objectMapper;
    }

    public YAMLMapper getYamlMapper() {
        YAMLMapper yamlMapper = new YAMLMapper();
        yamlMapper.enable(CLOSE_CLOSEABLE);
        yamlMapper.enable(MINIMIZE_QUOTES);
        yamlMapper.disable(WRITE_DOC_START_MARKER);

        return yamlMapper;
    }
}
