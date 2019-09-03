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
@TableName("t_test_record")
public class TTestRecordEntity {

    private static final long serialVersionUID = 1L;

    private Date createTime;

    private Date updateTime;

    private Integer testId;

    private String testName;

    private Date testTime;

    private Integer testsetId;

    private Integer modelId;

    private Integer resultId;

    private Integer userId;

    private String testNetwork;


}
