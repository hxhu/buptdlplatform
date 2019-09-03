package com.bupt.dlplatform.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhongling
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_config")
public class TConfigEntity  {

    private static final long serialVersionUID = 1L;

    private Date createTime;

    private Date updateTime;

    private Integer configId;

    private String configName;

    private Date configTime;

    private String network;

    private String configLocation;


}
