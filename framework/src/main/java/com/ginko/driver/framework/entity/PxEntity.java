package com.ginko.driver.framework.entity;

import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "image_px_info")
public class PxEntity implements Serializable {
    private static final long serialVersionUID = -2964339263179051386L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer userId;  //用户ID

    private Integer x;       //横坐标X

    private Integer y;       //纵坐标

    //rgba
    private Integer r;

    private Integer g;

    private Integer b;

    private Integer a;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public Integer getG() {
        return g;
    }

    public void setG(Integer g) {
        this.g = g;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }
}
