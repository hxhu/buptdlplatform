package com.bupt.dlplatform.mapper;


import com.bupt.dlplatform.model.MLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by huhx on 2020/11/22
 */
@Repository
public interface MLogEntityRepository extends MongoRepository<MLogEntity, String> {
    public MLogEntity findByType(Integer type);
}
