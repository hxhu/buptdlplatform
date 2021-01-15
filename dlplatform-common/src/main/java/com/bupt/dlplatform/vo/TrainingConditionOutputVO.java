package com.bupt.dlplatform.vo;

import lombok.*;

/**
 * Created by huhx on 2021/1/15
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrainingConditionOutputVO {
    private Integer currentIteratorTimes;

    private Integer maxIteratorTimes;

    private Double currentAccuracy;

    private String status; // running success error
}
