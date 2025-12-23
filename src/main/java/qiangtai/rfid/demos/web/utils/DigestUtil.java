package qiangtai.rfid.demos.web.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import qiangtai.rfid.demos.web.handler.exception.BusinessException;

public class DigestUtil {
    public static String get(String s) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        if (s == null){
            throw new BusinessException(9090,"参数不能为空");
        }
        return md5.digestHex(s);
    }
}
