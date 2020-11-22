package com.bupt.dlplatform.model.common;

import com.bupt.dlplatform.vo.MLogEntityInputVO;
import lombok.*;

/**
 * Created by huhx on 2020/11/22
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogElement {
    private String operation; // new delete modify
    private Long timestamp;
    private String message;

    public LogElement(MLogEntityInputVO data){
        this.operation = data.getOperation();
        this.timestamp = data.getTimestamp();
        this.message = data.getMessage();
    }
}
