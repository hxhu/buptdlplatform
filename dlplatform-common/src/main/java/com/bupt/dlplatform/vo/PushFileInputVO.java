package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.ArrayList;

/**
 * Created by huhx on 2020/12/17
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PushFileInputVO {
    private ArrayList<String> deviceIds;

    private String fileId;

    private String type;
}
