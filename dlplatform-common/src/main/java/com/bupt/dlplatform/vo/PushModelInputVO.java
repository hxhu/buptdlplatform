package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.ArrayList;

/**
 * Created by huhx on 2020/12/5
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PushModelInputVO {
    private ArrayList<String> deviceIds;

    private String modelId;

    private String type; // points  boardcast
}
