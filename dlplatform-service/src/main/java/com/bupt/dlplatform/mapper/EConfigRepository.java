package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.EConfigEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by huhx on 2021/2/5
 */
@Repository
public interface EConfigRepository extends MongoRepository<EConfigEntity, String> {
}
