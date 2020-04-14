package com.themagins.filemonitor.persistance.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Andris Magins
 * @created 15/01/2020
 **/
@Table(name = "path")
@Entity
public class Path implements Serializable {

    public Path() {
    }

    public Path(String real_path, String metadata, String catalog) {

        this.real_path = real_path;
        this.metadata = metadata;
        this.catalog = catalog;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    /*
        This is real path to file
     */
    @Column(name = "real_path")
    private String real_path;

    /*
        This path is for displaying files in GUI
     */

    @Column(name = "uri")
    private String uri;

    @Column(name = "display_path")
    private String displayPath;

    @Column(name = "metadata")
    private String metadata;

    @Column(name = "catalog")
    private String catalog;

    @Column(name = "is_online")
    private boolean isOnline;



    @OneToOne(fetch = FetchType.EAGER, optional = false)
    private Inode inode;

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getDisplayPath() {
        return displayPath;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setDisplayPath(String displayPath) {
        this.displayPath = displayPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReal_path() {
        return real_path;
    }

    public void setReal_path(String real_path) {
        this.real_path = real_path;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public Inode getInode() {
        return inode;
    }

    public void setInode(Inode inode) {
        this.inode = inode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path that = (Path) o;
        return id == that.id &&
                Objects.equals(real_path, that.real_path) &&
                Objects.equals(metadata, that.metadata) &&
                Objects.equals(catalog, that.catalog)  &&
                Objects.equals(inode, that.inode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, real_path, metadata, catalog);//, fileEntity);
    }

    @Override
    public String toString() {
        return "Path{" +
                "id=" + id +
                ", path='" + real_path + '\'' +
                ", metadata='" + metadata + '\'' +
                ", root='" + catalog + '\'' +
                '}';
    }
}
