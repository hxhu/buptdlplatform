package com.bupt.dlplatform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by huhx on 2020/10/3
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MDeviceEntityInputVO {
    @ApiModelProperty(value ="id")
    private String id;

    @ApiModelProperty(value ="ip")
    private InetAddress ip;

    @ApiModelProperty(value ="用户ID")
    private String userId;

    @ApiModelProperty(value ="类型")
    private String type; // sensor,embedded,server

    @ApiModelProperty(value ="设备名")
    private String name;

    @ApiModelProperty(value ="描述")
    private String desc;

    @ApiModelProperty(value ="状态")
    private Integer status; // 0-off  1-on  2-running

    @ApiModelProperty(value ="数据展示ID列表")
    private ArrayList<String> displayIds;

    @ApiModelProperty(value ="监控ID")
    private String monitorId;

    @ApiModelProperty(value ="注册时间")
    private Long registerTime;

    // collect收集数据
    @ApiModelProperty(value ="是否收集数据")
    private Boolean collectFlag;

    @ApiModelProperty(value ="最后收集时间")
    private Long lastCollectTime;
}
