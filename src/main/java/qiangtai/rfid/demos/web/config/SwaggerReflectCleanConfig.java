package qiangtai.rfid.demos.web.config;

import io.swagger.v3.oas.models.Components;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 一次性解决：Swagger 解析 java.lang.reflect.* 导致 StackOverflowError
 * 适用 JDK 8（1.8.0_60+）（自带 Parameter 类）
 */
@Configuration
public class SwaggerReflectCleanConfig {

    @Bean
    public OpenApiCustomiser removeReflectTypes() {
        return openApi -> {
            Components components = openApi.getComponents();
            if (components == null || components.getSchemas() == null) {
                return;
            }
            /* 只要 schema 名字以这些前缀开头就整表删除 */
            components.getSchemas().entrySet()
                    .removeIf(entry -> {
                        String key = entry.getKey();
                        return key.startsWith("Parameter")        // java.lang.reflect.Parameter
                                || key.startsWith("Method")
                                || key.startsWith("Executable")
                                || key.startsWith("Constructor")
                                || key.startsWith("Field")
                                || key.startsWith("HttpServletRequest")
                                || key.startsWith("HttpServletResponse")
                                || key.startsWith("MultipartFile");
                    });
        };
    }
}