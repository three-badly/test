package qiangtai.rfid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置内网放行
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 1. 内网任意 IP + 任意端口
        config.addAllowedOriginPattern("http://192.168.*.*:*");
        config.addAllowedOriginPattern("http://10.*.*.*:*");
        config.addAllowedOriginPattern("http://172.16.*.*:*");
        config.addAllowedOriginPattern("http://172.17.*.*:*");
        config.addAllowedOriginPattern("http://172.18.*.*:*");
        config.addAllowedOriginPattern("http://172.19.*.*:*");
        config.addAllowedOriginPattern("http://172.20.*.*:*");
        config.addAllowedOriginPattern("http://172.21.*.*:*");
        config.addAllowedOriginPattern("http://172.22.*.*:*");
        config.addAllowedOriginPattern("http://172.23.*.*:*");
        config.addAllowedOriginPattern("http://172.24.*.*:*");
        config.addAllowedOriginPattern("http://172.25.*.*:*");
        config.addAllowedOriginPattern("http://172.26.*.*:*");
        config.addAllowedOriginPattern("http://172.27.*.*:*");
        config.addAllowedOriginPattern("http://172.28.*.*:*");
        config.addAllowedOriginPattern("http://172.29.*.*:*");
        config.addAllowedOriginPattern("http://172.30.*.*:*");
        config.addAllowedOriginPattern("http://172.31.*.*:*");

        // 2. 允许带 cookie
        config.setAllowCredentials(true);

        // 3. 其余默认放行
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("Content-Disposition");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
/**
 * 跨域配置全部放行
 */
/*
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // 允许任何来源
        config.addAllowedMethod("*");        // 允许任何 HTTP 方法
        config.addAllowedHeader("*");        // 允许任何请求头
        config.addExposedHeader("*");        // 暴露任何响应头给前端
        // 注意：一旦用了 "*" 就不能再设置 allowCredentials=true，否则浏览器会拦截
        // config.setAllowCredentials(true); // ❌ 必须注释掉

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}*/
