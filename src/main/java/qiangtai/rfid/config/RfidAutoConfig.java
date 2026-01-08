package qiangtai.rfid.config;

import com.rscja.deviceapi.RFIDWithUHFNetworkA4;
import com.rscja.deviceapi.entity.AntennaNameEnum;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.interfaces.IUHFInventoryCallback;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * 完全适配 Spring Boot 2.7.6 + JDK8
 * @author 16623
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "rfid")
public class RfidAutoConfig {

    private String host;
    private int port;
    private String antenna;
    private int power;

    private final RFIDWithUHFNetworkA4 rfid = new RFIDWithUHFNetworkA4();

    /* ====== 生命周期 ====== */
    @PostConstruct
    public void start() {
        // 1. 连接
        if (!rfid.init(host, port)) {
            log.error("❌ RFID 连接失败 {}:{}", host, port);
            throw new IllegalStateException("RFID 连接失败");
        }else {
            log.info("✅ RFID 已连接 {}:{}", host, port);
        }
        // 2. 基础参数
        rfid.setPower(AntennaNameEnum.valueOf(antenna), power);
        // 3. 回调 + 盘点
        rfid.setInventoryCallback(this::handleTag);
        rfid.startInventoryTag();
        log.info("✅ RFID 已连接并启动盘点 {}:{} antenna={} power={}", host, port, antenna, power);
    }

    @PreDestroy
    public void stop() {
        rfid.stopInventory();
        rfid.free();
        executor.shutdown();
        log.info("🔌 RFID 已释放");
    }

    /* ====== 线程池：标签数据推业务 ====== */
    private final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(2048),
                    r -> new Thread(r, "rfid-tag"));

    public void handleTag(UHFTAGInfo tag) {
        executor.execute(() -> {
            log.info("🏷️ EPC={} RSSI={}", tag.getEPC(), tag.getRssi());
            // TODO：写库 / 发 WebSocket / 发 MQ
            // SpringContext.getBean(AccessLogsService.class).save(tag.getEPC(), tag.getRssi());
        });
    }

    /* ====== 让别的 Service 也能拿到实例（可选） ====== */
    @Bean
    public RFIDWithUHFNetworkA4 rfidInstance() {
        return rfid;
    }
}