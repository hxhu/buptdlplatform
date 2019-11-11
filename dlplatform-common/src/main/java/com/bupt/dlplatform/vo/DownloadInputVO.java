package com.bupt.dlplatform.vo;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DownloadInputVO extends Serializers.Base {
    private  String resultId;
    private String  localLocation;
    private String downloadPath;
}

