package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.MDeviceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by huhx on 2020/10/3
 */
public interface MDeviceEntityRepository extends MongoRepository<MDeviceEntity, String> {
}
