package com.bupt.dlplatform.service.impl;

import com.bupt.dlplatform.mapper.MDataEntityRepository;
import com.bupt.dlplatform.mapper.TAnalyseRecordRepository;
import com.bupt.dlplatform.service.DataDisplayService;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huhx on 2020/9/29
 */
@Slf4j
@Service
public class DataDisplayServiceImpl implements DataDisplayService {
    @Autowired
    private MDataEntityRepository mDataEntityRepository;

    /**
     * 保存数据
     */
    @Override
    public ResponseVO saveData(){
        mDataEntityRepository.save();
    }
}
