package com.bupt.dlplatform.vo;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by huhx on 2020/12/4
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EModelInputVO {
    private String id;

    private String modelName;

    private String modelDesc;

    private Long createTime;

    private String modelLocation;
}
