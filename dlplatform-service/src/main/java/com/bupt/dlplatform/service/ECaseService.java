package com.bupt.dlplatform.service;

import com.bupt.dlplatform.vo.*;

/**
 * Created by huhx on 2020/12/23
 */
public interface ECaseService {
    /**
     * 增加用例
     *
     * @return
     */
    public ResponseVO addECase(ECaseInputVO eCaseInputVO);

    /**
     * 修改用例
     *
     * @return
     */
    public ResponseVO updateECase(ECaseInputVO eCaseInputVO);

    /**
     * 查询用例
     * Id方式
     *
     * @return
     */
    public ResponseVO<ECaseOutputVO> getECase(String caseId);

    /**
     * 删除用例
     *
     * @return
     */
    public ResponseVO deleteECase(String caseId);
}
