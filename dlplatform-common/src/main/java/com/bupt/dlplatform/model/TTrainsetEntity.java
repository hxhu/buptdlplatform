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
@TableName("t_trainset")
public class TTrainsetEntity {

    private static final long serialVersionUID = 1L;

    private Date createTime;

    private Date updateTime;

    private String trainsetId;

    private String trainsetName;

    private Date trainsetTime;

    private String classNum;

    private String trainsetLocation;

    /**
     * 0表示通用
     */
    private Boolean trainsetCommon;

    private String trainsetType;

    private String description;


}
