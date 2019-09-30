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

    private String testId;

    private String testName;

    private Date testTime;

    private String testsetId;

    private String modelId;

    private String resultId;

    private String userId;

    private String testNetwork;

    private String configId;

    private double threshold;


}
