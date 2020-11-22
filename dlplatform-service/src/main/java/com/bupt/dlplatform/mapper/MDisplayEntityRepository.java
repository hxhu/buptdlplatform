package com.bupt.dlplatform.mapper;

import com.bupt.dlplatform.model.MDisplayEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by huhx on 2020/10/3
 */
@Repository
public interface MDisplayEntityRepository extends MongoRepository<MDisplayEntity, String> {
    public MDisplayEntity findByIdAndType( String id, String type);
}
