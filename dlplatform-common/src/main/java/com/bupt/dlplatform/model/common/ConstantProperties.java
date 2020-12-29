package com.bupt.dlplatform.model.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by huhx on 2020/12/29
 */
//指定常量资源路径
@PropertySource("classpath:application-dev.yml")
@Component
public class ConstantProperties {
    @Value("${dataset.rootPath}")
    private String rootPath;

    @Value("${dataset.vocJPGPath}")
    private String vocJPGPath;

    @Value("${dataset.vocXMLPath}")
    private String vocXMLPath;

    @Value("${dataset.vocLabelPath}")
    private String vocLabelPath;

    @Value("${dataset.vocTxtPath}")
    private String vocTxtPath;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getVocJPGPath() {
        return vocJPGPath;
    }

    public void setVocJPGPath(String vocJPGPath) {
        this.vocJPGPath = vocJPGPath;
    }

    public String getVocXMLPath() {
        return vocXMLPath;
    }

    public void setVocXMLPath(String vocXMLPath) {
        this.vocXMLPath = vocXMLPath;
    }

    public String getVocLabelPath() {
        return vocLabelPath;
    }

    public void setVocLabelPath(String vocLabelPath) {
        this.vocLabelPath = vocLabelPath;
    }

    public String getVocTxtPath() {
        return vocTxtPath;
    }

    public void setVocTxtPath(String vocTxtPath) {
        this.vocTxtPath = vocTxtPath;
    }
}
