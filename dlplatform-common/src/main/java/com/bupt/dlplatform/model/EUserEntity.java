package com.bupt.dlplatform.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;

/**
 * Created by huhx on 2020/12/4
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "e_user")
public class EUserEntity {
    @Id
    private String id;

    private String userName;

    private String password;

    private String phoneNumber;

    private String userType = "user"; // user--使用者  developer--开发者  root--超级权限

    private Integer isDeleted = 0; // 0--正常  1--删除
}
