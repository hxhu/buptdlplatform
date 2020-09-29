package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.MDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Created by huhx on 2020/9/28
 */
@Repository
public interface MDataEntityRepository extends MongoRepository<MDataEntity,String> {
}
