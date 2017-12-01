package org.craftercms.profile;


import org.craftercms.commons.http.RequestContextBindingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { WebMvcAutoConfiguration.class })
@ImportResource({"classpath:/crafter/profile/services-context.xml",
                 "classpath:/crafter/profile/web-context.xml"})
public class ProfileApplicationMain {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProfileApplicationMain.class);
        app.setWebEnvironment(true);
        app.run(args);
    }

    @Bean
    public FilterRegistrationBean requestContextBindingFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestContextBindingFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestContextBindingFilter");
        registration.setOrder(1);
        return registration;
    }
}
