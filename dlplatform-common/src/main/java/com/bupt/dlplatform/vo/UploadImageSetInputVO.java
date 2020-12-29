package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.List;

/**
 * Created by huhx on 2020/12/29
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UploadImageSetInputVO {
    List<String> nameList;

    String dataSetId;
}
