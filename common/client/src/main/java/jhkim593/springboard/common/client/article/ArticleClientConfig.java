package jhkim593.springboard.common.client.article;


import feign.Feign;
import feign.RedirectionInterceptor;
import feign.Retryer;
import feign.jackson.JacksonEncoder;
import jhkim593.springboard.common.client.FeignErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArticleClientConfig {
    @Value("${endpoints.article-service}")
    private String articleServiceUrl;


    private Feign.Builder feignBuilder;

    @Bean
    public ArticleClient articleClient() {
        return feignBuilder.target(ArticleClient.class, articleServiceUrl);
    }

    public ArticleClientConfig() {
        this.feignBuilder = Feign.builder()
                .encoder(new JacksonEncoder())
//                .requestInterceptor(requestInterceptor())
                .errorDecoder(new FeignErrorDecoder())
                //1초 간격으로 시작해 최대 3초간격으로 점점 증가하며 최대 3번 재시도
                .retryer(new Retryer.Default(1000l,3000l,3))
                .responseInterceptor(new RedirectionInterceptor());
    }

//    private RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            requestTemplate.header("Authorization", "Bearer "+token);
//            requestTemplate.header("Content-Type", "application/json");
//        };
//    }
}
