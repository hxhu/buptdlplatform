package com.bupt.dlplatform.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
@TableName("t_testset")
public class TTestsetEntity {

    private static final long serialVersionUID = 1L;

    private Date createTime;

    private Date updateTime;

    private Integer testsetId;

    private String testsetName;

    private Date testsetTime;

    private Double testsetSize;

    private String testsetLocation;


}
