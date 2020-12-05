package com.bupt.dlplatform.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by huhx on 2020/12/4
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "e_model")
public class EModelEntity {
    @Id
    private String id;

    private String modelName;

    private String modelDesc;

    private Long createTime;

    private String modelLocation;

    private Integer isDeleted = 0;
}
