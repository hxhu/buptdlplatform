package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.EModelEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by huhx on 2020/12/4
 */
@Repository
public interface EModelRepository extends MongoRepository<EModelEntity, String> {
}
