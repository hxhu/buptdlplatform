package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.common.LogElement;
import lombok.*;

/**
 * Created by huhx on 2020/11/22
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MLogEntityOutputVO {
    private String operation; // new delete modify
    private Long timestamp;
    private String message;

    public MLogEntityOutputVO(LogElement logElement){
        this.operation = logElement.getOperation();
        this.timestamp = logElement.getTimestamp();
        this.message = logElement.getMessage();
    }
}
