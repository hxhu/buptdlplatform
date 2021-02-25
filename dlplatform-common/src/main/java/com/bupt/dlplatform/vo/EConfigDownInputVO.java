package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.ArrayList;

/**
 * Created by huhx on 2021/2/18
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EConfigDownInputVO {
    private String configId;

    private ArrayList<String> deviceIds;
}
