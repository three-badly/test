package qiangtai.rfid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RfidApplication {

    private static final Logger log = LoggerFactory.getLogger(RfidApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RfidApplication.class, args);
        log.info("RFID系统启动成功\nhttp://localhost:8084/doc.html");
    }

}
