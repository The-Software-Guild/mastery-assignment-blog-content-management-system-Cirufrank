package com.we.blogcms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = CommandLineRunner.class))
@EnableAutoConfiguration
public class TestApplicationConfiguration {

        //We don't need anything more than this since it only exists to start up our tests 
        //without running the main program
    
        //GIVE TESTS THIS ANNOTATION: @SpringBootTest(classes = TestApplicationConfiguration.class)

}
