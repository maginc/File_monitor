package com.themagins.filemonitor.persistance.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Andris Magins
 * @created 16/01/2020
 **/
@Table(name = "metadata")
@Entity
public class Metadata implements Serializable {
    public Metadata() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "metadata")
    private String metadata;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Inode inode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Inode getInode() {
        return inode;
    }

    public void setInode(Inode inode) {
        this.inode = inode;
    }
}
