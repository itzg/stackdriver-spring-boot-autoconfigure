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

import io.micrometer.core.instrument.Clock;
import io.micrometer.stackdriver.StackdriverConfig;
import io.micrometer.stackdriver.StackdriverMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({ CompositeMeterRegistryAutoConfiguration.class,
    SimpleMetricsExportAutoConfiguration.class })
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnBean(Clock.class)
@ConditionalOnClass(StackdriverMeterRegistry.class)
@ConditionalOnProperty(prefix = "management.metrics.export.stackdriver", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(StackdriverProperties.class)
public class StackdriverMetricsExportAutoConfiguration {

  private final StackdriverProperties properties;

  @Autowired
  public StackdriverMetricsExportAutoConfiguration(StackdriverProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public StackdriverConfig stackdriverConfig() {
    return new StackdriverPropertiesConfigAdapter(this.properties);
  }

  @Bean
  @ConditionalOnMissingBean
  public StackdriverMeterRegistry stackdriverMeterRegistry(StackdriverConfig config,
      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Clock clock) {
    return StackdriverMeterRegistry.builder(config).clock(clock)
        .build();
  }
}
