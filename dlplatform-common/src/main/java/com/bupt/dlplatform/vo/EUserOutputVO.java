package com.bupt.dlplatform.vo;

import com.bupt.dlplatform.model.EUserEntity;
import lombok.*;

/**
 * Created by huhx on 2020/12/4
 */
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EUserOutputVO {
    private String id;

    private String userName;

    private String password;

    private String phoneNumber;

    private String userType;

    public EUserOutputVO( EUserEntity eUserEntity ){
        this.id = eUserEntity.getId();
        this.userName = eUserEntity.getUserName();
        this.password = eUserEntity.getPassword();
        this.phoneNumber = eUserEntity.getPhoneNumber();
        this.userType = eUserEntity.getUserType();
    }
}
