package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ModelUploadInputVO {
    private String fileName;
    private String filePath;
    private Date uploadTime;
    private String status;
}
