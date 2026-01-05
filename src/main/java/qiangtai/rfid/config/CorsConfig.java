package qiangtai.rfid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowCredentials(true)
                .maxAge(3600);
    }
}
/**
 * @author FEI
 * 跨域配置
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration cfg = new CorsConfiguration();
        // 可选 1. 使用 originPattern 支持通配，同时允许凭证
        // 通配，不与 credentials 冲突
        cfg.addAllowedOriginPattern("*");
//        可选2 指定允许的 origin
//        cfg.addAllowedOrigin("http://localhost:8080");
        cfg.addAllowedHeader("*");
        cfg.addAllowedMethod("*");
        // 允许前端带 cookie/token
        cfg.setAllowCredentials(true);
        // 预检缓存 1 小时
        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        // 注册到过滤器链
        return new CorsFilter(source);
    }
}*/
