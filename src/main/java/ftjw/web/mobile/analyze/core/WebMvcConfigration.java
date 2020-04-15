package ftjw.web.mobile.analyze.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 殷晓龙
 * 2020/3/17 11:06
 */
@Configuration
public class WebMvcConfigration implements WebMvcConfigurer {

    /**
     * 解除跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("POST","GET").maxAge(3600)
                .allowCredentials(true);
    }

}
