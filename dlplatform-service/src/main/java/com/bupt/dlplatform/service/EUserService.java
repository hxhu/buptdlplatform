package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

/**
 * Created by huhx on 2020/12/4
 */
public interface EUserService {
    /**
     * 增加用户
     * @return
     */
    public ResponseVO addEUser(EUserInputVO eUserInputVO);

    /**
     * 修改用户
     * @return
     */
    public ResponseVO updateEUser(EUserInputVO eUserInputVO);

    /**
     * 查询用户
     * Id方式
     * @return
     */
    public ResponseVO<EUserOutputVO> getEUser(String userId);

    /**
     * 删除用户
     * @return
     */
    public ResponseVO deleteEUser(String userId);
}
