package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.data.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 必须提供一个默认空的构造子,否则远程调用会抛出以及实现Serializable,便于能序列化
 * 或者标注@NoArgsConstructor
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("响应实体")
public class ResponseVO<T> implements Serializable {
    private static final long seriaVersionUID=1L;
    @ApiModelProperty("编码 2000:成功，其他异常")
    private int code;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("数据结果集")
    private T data;

    @ApiModelProperty("服务时间戳")
    private Long date;

    public ResponseVO(ResponseCode responseCode){
        this.code = responseCode.value();
        this.msg = responseCode.getDescription();
    }
    public ResponseVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.date = System.currentTimeMillis();
    }
    public ResponseVO(ResponseCode responseCode, T data) {
        this.code = responseCode.value();
        this.msg = responseCode.getDescription();
        this.data = data;
        this.date = System.currentTimeMillis();
    }
}
