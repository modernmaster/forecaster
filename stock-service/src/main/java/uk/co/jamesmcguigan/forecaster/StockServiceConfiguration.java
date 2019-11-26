package uk.co.jamesmcguigan.forecaster;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class StockServiceConfiguration {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String PATCH = "PATCH";
    private static final String ALLOWED_HEADER = "*";

    @Bean
    public FilterRegistrationBean<CorsFilter> corsConfigurer() {
        //This should work, but...
//    https://stackoverflow.com/questions/38664507/cant-enable-cors-in-spring-boot-data-rest        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("*")
//                        .allowedMethods("PUT", "DELETE", "GET", "POST");;
//            }
//        };
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader(ALLOWED_HEADER);
        config.addAllowedMethod(GET);
        config.addAllowedMethod(POST);
        config.addAllowedMethod(PUT);
        config.addAllowedMethod(PATCH);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

}
