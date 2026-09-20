package com.contractlens.common.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoaderTemplate {
    public static String loadTemplate(String path) throws IOException {

        ClassPathResource resource = new ClassPathResource(path);

        return new String(
                resource.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
    }
}
