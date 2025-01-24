package com.qikserve.checkout_api.integrationtests.testcontainers;

import com.qikserve.checkout_api.configs.TestConfigs;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        private static final Logger logger = Logger.getLogger(AbstractIntegrationTest.class.getName());

        private static final GenericContainer<?> wiremockContainer = new GenericContainer<>(TestConfigs.WIREMOCK_IMAGE)
                .withExposedPorts(TestConfigs.WIREMOCK_PORT)
                .waitingFor(Wait.forHttp("/__admin").forStatusCode(200));

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testContainers = new MapPropertySource(
                    "testcontainers",
                    getWiremockConfiguration()
            );
            environment.getPropertySources().addFirst(testContainers);
        }

        private static Map<String, Object> getWiremockConfiguration() {
            return Map.of(
                    "wiremock.base-url",
                    "http://" + wiremockContainer.getHost() + ":" + wiremockContainer.getMappedPort(TestConfigs.WIREMOCK_PORT)
            );
        }

        private static void startContainers() {
            logger.info("Starting WireMock container...");
            Startables.deepStart(Stream.of(wiremockContainer)).join();
            int wiremockPort = wiremockContainer.getMappedPort(TestConfigs.WIREMOCK_PORT);
            String wiremockHost = wiremockContainer.getHost();
            logger.info("WireMock container started on host: " + wiremockHost + " port: " + wiremockPort);
        }
    }
}
