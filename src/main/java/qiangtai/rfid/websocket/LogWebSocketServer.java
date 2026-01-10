package qiangtai.rfid.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

@ServerEndpoint("/ws/log/{sessionId}")
@Component
public class LogWebSocketServer {

    private static final ConcurrentHashMap<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    @Value("${logging.file.name:logs/rfid/rfid.log}")
    private String logPath;

    /* 每个连接独立线程池 */
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") String sessionId) {
        SESSION_MAP.put(sessionId, session);
        // 首次推送：把当前已有内容发出去
        executor.execute(() -> tailAndPush(session));
        // 再定时增量推送
        executor.scheduleWithFixedDelay(() -> tailAndPush(session), 1, 1, TimeUnit.SECONDS);
    }

    @OnClose
    public void onClose(@PathParam("sessionId") String sessionId) {
        SESSION_MAP.remove(sessionId);
        executor.shutdown();   // 只关自己的线程池
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("WebSocket 错误: " + error.getMessage());
    }

    /* ---------- 增量推送 ---------- */
    private long lastLen = 0;

    private void tailAndPush(Session session) {
        if (!session.isOpen()) return;
        try (RandomAccessFile raf = new RandomAccessFile(logPath, "r")) {
            long fileLen = raf.length();
            if (fileLen < lastLen) lastLen = 0;          // 日志被清空
            if (fileLen > lastLen) {
                raf.seek(lastLen);
                byte[] buf = new byte[(int) (fileLen - lastLen)];
                raf.readFully(buf);
                lastLen = fileLen;
                String txt = new String(buf, StandardCharsets.UTF_8);
                session.getBasicRemote().sendText(txt);
            }
        } catch (IOException e) {
            // 客户端已断开，关闭会话
            try {
                session.close();
            } catch (IOException ignored) {}
        }
    }
}