package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.MMonitorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by huhx on 2020/10/3
 */
@Repository
public interface MMonitorEntityRepository extends MongoRepository<MMonitorEntity, String> {
}
