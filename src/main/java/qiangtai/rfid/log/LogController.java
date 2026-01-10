package qiangtai.rfid.log;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import qiangtai.rfid.utils.LogTailUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/log")
@Tag(name = "服务器日志接口")
public class LogController {

    @Value("${logging.file.name:logs/rfid/rfid.log}")
    private String logPath;

    @GetMapping("/realtime")
    @Operation(summary = "实时日志")
    public List<String> realtime(
            @RequestParam(defaultValue = "100") int lines) throws Exception {
        File logFile = new File(logPath);
        if (!logFile.exists()) {
            return Collections.singletonList("日志文件不存在：" + logFile.getAbsolutePath());
        }
        return LogTailUtil.tail(logFile, lines);
    }
}