package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.ELogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huhx on 2020/12/15
 */
@Repository
public interface ELogRepository extends MongoRepository<ELogEntity, String> {
    List<ELogEntity> findAllByModelId(String modelId);

    List<ELogEntity> findAllByDeviceId(String deviceId);
}
