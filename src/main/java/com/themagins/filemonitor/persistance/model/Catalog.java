package com.themagins.filemonitor.persistance.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Andris Magins
 * @created 17/01/2020
 **/

@Component
@Entity
@Table(name = "catalog")
public class Catalog implements Serializable {

    public Catalog() {
    }

    public Catalog(String name, String rootPath) {
        this.name = name;
        this.rootPath = rootPath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added_at")
    @CreatedDate
    private Date addedAt = new Timestamp(System.currentTimeMillis());


    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "root_path", unique = true)
    private String rootPath;

    @Column(name = "ip")
    private String ip;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", addedAt=" + addedAt +
                ", name='" + name + '\'' +
                ", rootPath='" + rootPath + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
