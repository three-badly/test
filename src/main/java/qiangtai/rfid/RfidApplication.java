package qiangtai.rfid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class RfidApplication {

    private static final Logger log = LoggerFactory.getLogger(RfidApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(RfidApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("RFID系统启动成功\n");
        String port = env.getProperty("server.port");
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        log.info("本机地址:\thttp://{}:{}", hostAddress, port);
        log.info("接口地址:\thttp://{}:{}/doc.html", hostAddress, port);

    }

}
