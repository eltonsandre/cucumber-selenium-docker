package com.github.eltonsandre;


import org.junit.ClassRule;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

public class SimpleTest {

    @ClassRule
    public static DockerComposeContainer<?> compose =
            new DockerComposeContainer<>(
                    new File("src/test/resources/test-compose.yml"))
                    .withExposedService("simpleWebServer_1", 80);

}
