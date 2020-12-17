package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.EFileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by huhx on 2020/12/16
 */
@Repository
public interface EFileRepository extends MongoRepository<EFileEntity, String> {
}
