package com.github.epay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class EpayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EpayServiceApplication.class, args);
    }

}
