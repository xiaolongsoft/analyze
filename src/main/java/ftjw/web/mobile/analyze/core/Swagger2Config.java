package ftjw.web.mobile.analyze.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerAPI配置
 * 2020/4/13 14:26
 * @author 殷晓龙
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包下controller生成API文档
                .apis(RequestHandlerSelectors.basePackage("ftjw.web.mobile.analyze.controller"))
                .paths(PathSelectors.any())
                .build()
                ;

    }


    /**
     * api基本信息
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("移动化项目文档")
                .description("自动文档功能测试")
                .contact(contact())
                .version("version1")
                .license("飞天经纬")
                .licenseUrl("http://www.ftjw.cn/")
                .build();
    }

    /**
     * 联系人信息
     * @return
     */
    private Contact contact(){
        return new Contact("xiaolong","http://www.ftjw.cn/","xiaolongsoft@qq.com");
    }

}


