package com.bupt.dlplatform.model;;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhongling
 * @since 2019-09-18
 */
@Data

@Accessors(chain = true)
@TableName("t_testset_record")
public class TTestsetRecordEntity {

    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String testsetId;

    private String userId;


}
