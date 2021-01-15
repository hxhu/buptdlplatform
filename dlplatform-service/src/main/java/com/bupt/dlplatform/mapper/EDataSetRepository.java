package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.ECaseEntity;
import com.bupt.dlplatform.model.EDataSetEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by huhx on 2020/12/23
 */
@Repository
public interface EDataSetRepository extends MongoRepository<EDataSetEntity, String> {
    boolean existsById(String id);
}
