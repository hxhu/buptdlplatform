package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.MMonitorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by huhx on 2020/10/3
 */
public interface MMonitorEntityRepository extends MongoRepository<MMonitorEntity, String> {
}
