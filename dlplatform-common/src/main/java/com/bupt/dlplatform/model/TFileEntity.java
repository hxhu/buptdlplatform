package com.bupt.dlplatform.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("t_file")
public class TFileEntity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    /**
     *
     */
    private Integer patchIndex;

    /**
     *
     */
    private String parent;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String path;

    /**
     *
     */
    private String md5;

    /**
     *
     */
    private Long size;

    /**
     *
     */
    private Date createTime;

    private String fileId;
}
