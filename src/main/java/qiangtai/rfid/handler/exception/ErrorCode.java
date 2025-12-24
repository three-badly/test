package qiangtai.rfid.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author cwp
 * @date 2024-03-14 11:39
 */
@AllArgsConstructor
@Getter
@ToString
public enum ErrorCode {

    /**
     * code  错误码
     * message 详细的错误信息
     */
    KAFKA_SEND_FAIL(10000, "kafka消息发送异常"),
    MINIO_UPLOAD_FAIL(10001, "上传文件失败"),
    ORDER_NUMBER_NULL_FAIL(10002, "订单号为空或不可录入，不存在"),
    ORDER_NUMBER_FIGURE_NULL_FAIL(10003, "订单号，图号不能为空"),
    FIGURE_NULL_FAIL(10004, "图号不存在"),
    ORDER_NULL_FAIL(10005, "订单号不存在"),
    MATERIAL_WEIGHT_GREATER_THAN_PRODUCT_WEIGHT(10006, "原料重量必须大于成品重量"),

    END(99999, "END");

    /**
     * 错误码
     */
    private final int code;

    /**
     * 详细的错误信息
     */
    private final String message;
}
