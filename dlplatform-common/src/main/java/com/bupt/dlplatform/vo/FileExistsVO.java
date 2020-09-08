package com.bupt.dlplatform.vo;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FileExistsVO {

    /**
     ** 文件 id
     **/
    private String id;

    /**
     * 文件状态
     *      -1: 不存在
     *       1: 已存在
     *       0: 部分存在
     */
    private Integer status;

    /**
     * 已上传的文件分片索引
     */
    private List<Integer> patchIndex;

    public static FileExistsVO nonExistent() {
        return new FileExistsVO(null, -1, null);
    }

    public static FileExistsVO exists(String id) {
        return new FileExistsVO(id, 1, null);
    }

    public static FileExistsVO partExistent(String id, List<Integer> patchIndex) {
        return new FileExistsVO(id, 0, patchIndex);
    }
}
