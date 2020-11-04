package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.MDeviceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

/**
 * Created by huhx on 2020/10/3
 */
public interface MDeviceEntityRepository extends MongoRepository<MDeviceEntity, String> {
    public ArrayList<MDeviceEntity> findByUserId(String userId);

}
