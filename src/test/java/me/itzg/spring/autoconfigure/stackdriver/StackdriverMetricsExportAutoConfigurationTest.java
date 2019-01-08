/*
 *    Copyright 2019 Geoff Bourne
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package me.itzg.spring.autoconfigure.stackdriver;

import static org.assertj.core.api.Assertions.assertThat;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MockClock;
import io.micrometer.stackdriver.StackdriverMeterRegistry;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Tests {@link StackdriverMetricsExportAutoConfiguration} using the strategy
 * <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html#boot-features-test-autoconfig">documented by Spring Boot</a>
 */
public class StackdriverMetricsExportAutoConfigurationTest {
  private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
      .withConfiguration(AutoConfigurations.of(StackdriverMetricsExportAutoConfiguration.class));

  @Configuration
  static class TestConfig {
    @Bean
    public Clock micrometerClock() {
      return new MockClock();
    }
  }

  @Test
  public void meterRegistryCreated() {
    this.contextRunner
        .withUserConfiguration(TestConfig.class)
        .run(context -> {
          assertThat(context).hasSingleBean(StackdriverMeterRegistry.class);
        });
  }
}