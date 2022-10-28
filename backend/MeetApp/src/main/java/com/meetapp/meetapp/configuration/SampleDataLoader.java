package com.meetapp.meetapp.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@ConditionalOnProperty(name = "useSampleData", havingValue = "true")
public class SampleDataLoader implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
