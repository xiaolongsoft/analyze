package ftjw.web.mobile.analyze.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 殷晓龙
 * 2020/3/17 11:06
 */
@Configuration
public class WebConf extends WebMvcConfigurationSupport {

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST","GET").maxAge(3600)
                .allowCredentials(true);
        super.addCorsMappings(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/resources/")
                    .addResourceLocations("classpath:/static/")
                    .addResourceLocations("classpath:/public/");
            super.addResourceHandlers(registry);
    }

/*    *//**
     * 解决response返回中文出现乱码问题
     * @param converters
     *//*
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        super.configureMessageConverters(converters);
    }*/
}
