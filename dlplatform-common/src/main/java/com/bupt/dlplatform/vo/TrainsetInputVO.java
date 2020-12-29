package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TrainsetInputVO extends BaseInputVO {
    Date uploadTime;
    String trainsetId;
    Double size;
    String pathname;
    String description;
    String trainsetName;
}
