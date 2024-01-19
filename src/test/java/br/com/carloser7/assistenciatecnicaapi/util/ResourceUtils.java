package br.com.carloser7.assistenciatecnicaapi.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ResourceUtils {

    public static String getContentFromResource(String resourcePath) {
        try {
            InputStream stream = org.springframework.util.ResourceUtils.class.getResourceAsStream(resourcePath);
            return StreamUtils.copyToString(stream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}