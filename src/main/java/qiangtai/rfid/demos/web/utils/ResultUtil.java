package qiangtai.rfid.demos.web.utils;


import qiangtai.rfid.demos.web.dto.result.Result;

/**
 * @author cwp
 * @create 2020-04-15 17:21
 */
public class ResultUtil {
    
    public static Result success() {
        return success("");
    }
    
    public static Result success(String msg) {
        Result result = new Result();
        result.setCode(0);
        result.setMessage(msg);
        return result;
    }
    
    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(0);
        result.setData(data);
        return result;
    }
    
    public static Result successJsonStr(String data) {
        Result result = new Result();
        result.setCode(0);
        result.setData(data);
        return result;
    }
    
    public static Result error(int code, String message) {
        return error(code, message, null);
    }
    
    public static Result error(int code, String message, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    
}
