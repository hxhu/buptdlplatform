package com.bupt.dlplatform.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import lombok.Data;
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
@Accessors(chain = true)
@TableName("t_model")
public class TModelEntity {

    private static final long serialVersionUID = 1L;

    private String modelId;

    private String modelName;

    private Date modelTime;

    private String network;

    private Date createTime;

    private Date updateTime;

    private String task;

    private String modelLocation;

    /**
     * 0表示通用
     */
    private Boolean modelCommon;

    private String modelLabel;



}
