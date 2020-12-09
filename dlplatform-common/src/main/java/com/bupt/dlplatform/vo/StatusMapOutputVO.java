package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.List;

/**
 * Created by huhx on 2020/12/10
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StatusMapOutputVO {
    private List<String> deviceIds;
}
