package br.ita.bditac.ws.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "br.ita.bditac")
public class Application {

    @Bean
    public DispatcherServletBeanPostProcessor dispatcherServletBeanPostProcessor() {
        return new DispatcherServletBeanPostProcessor();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
