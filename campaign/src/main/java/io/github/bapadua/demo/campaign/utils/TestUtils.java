package io.github.bapadua.demo.campaign.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;

/**
 * @author ice_bpadua on 26/09/2020
 */
public class TestUtils {
    private static final String ROOT_RESOURCE_PATH = "/static/mock/";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T loadFile(final String path, final Class<T> valueType) throws Exception {
        final ClassPathResource resource = new ClassPathResource(ROOT_RESOURCE_PATH + path);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(resource.getInputStream(), valueType);
    }

    public static <T> T[] loadFiles(final String path, final Class<T[]> valueType) throws Exception {
        final ClassPathResource resource = new ClassPathResource(ROOT_RESOURCE_PATH + path);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(resource.getInputStream(), valueType);
    }

    public static <T> T loadMock(final String path, final String fileName, Class<T> type) throws Exception {
        return loadFile(path + "/" + fileName, type);
    }
}
