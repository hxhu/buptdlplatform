package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestsetTempVO extends BaseInputVO{
    Date uploadTime;
    String testsetId;
    Double size;
    String pathname;
}
