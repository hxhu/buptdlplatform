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
@Accessors(chain = true)
@TableName("t_test_result")
public class TTestResultEntity {

    private static final long serialVersionUID = 1L;

    private Date createTime;

    private Date updateTime;

    private String resultId;

    private String resultName;

    private String resultLocation;

    private Date resultTime;


}
