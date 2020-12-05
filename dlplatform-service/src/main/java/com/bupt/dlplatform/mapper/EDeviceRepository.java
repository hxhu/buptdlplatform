package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.EDeviceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by huhx on 2020/12/4
 */
@Repository
public interface EDeviceRepository extends MongoRepository<EDeviceEntity, String> {
}
