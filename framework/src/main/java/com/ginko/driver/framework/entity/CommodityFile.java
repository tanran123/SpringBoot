package com.ginko.driver.framework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: tran
 * @Date: 2019/11/21
 * @Description:
 */
@Entity
@Table(name = "commodity_file")
public class CommodityFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String collectTime;

    private String name;

    @Transient
    @JsonIgnore
    private int fileTypeId;

    private Double fileSize;

    private String fileLocation;

    @Column(name = "commodity_number")
    private String commodityNumber;

    @Transient
    @JsonIgnore
    private String secret;

    @Transient
    @JsonIgnore
    private int ownerId;

    private String  txId;

    @Transient
    @JsonIgnore
    private Double photoSize;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期时间格式
        String format = dateFormat.format(collectTime).replaceAll(".0","");//格式化一下
        this.collectTime = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(int fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(String commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Double getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(Double photoSize) {
        this.photoSize = photoSize;
    }
}
