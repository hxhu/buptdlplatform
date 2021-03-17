package com.bupt.dlplatform.Entity;

import lombok.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by huhx on 2020/12/6
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DownEntity {
    private String command; // "sendModel,boardcase"   "sendModel,points"

    private String data; // "/models,/yolov3.weights,1234567890"

    private List<String> target; // "null"  "xxx,yyy,zzz"

    private Long timestamp;

    private HashMap<String, Object> extra;
}
