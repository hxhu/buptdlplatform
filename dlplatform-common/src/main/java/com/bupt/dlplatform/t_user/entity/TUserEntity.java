package com.bupt.dlplatform.t_user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bupt.dlplatform.model.base.SuperEntity;
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
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user")
public class TUserEntity extends SuperEntity {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String userName;

    private String password;

    private String phoneNumber;

    private String userType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
