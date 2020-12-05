package com.bupt.dlplatform.vo;

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
public class EUserInputVO {
    private String id;

    private String userName;

    private String password;

    private String phoneNumber;

    private String userType;
}
