package com.themagins.filemonitor.persistance;

import java.io.Serializable;

/**
 * @author Andris Magins
 * @created 28-Mar-20
 **/
public class Inode implements Serializable {
    private String name;
    private String parent;
    private boolean is_file;
    private Long id;
    private String root_catalog;
    private String metadata;
    private long size;
    private String file_path;



    public Inode(String name, String parent, boolean is_file, Long id, String root_catalog, String metadata, Long size, String file_path) {
        this.name = name;
        this.parent = parent;
        this.is_file = is_file;
        this.id = id;
        this.root_catalog = root_catalog;
        this.metadata = metadata;
        this.size = size;
        this.file_path = file_path;

    }

    @Override
    public String toString() {
        return "Inode{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", is_file=" + is_file +
                ", id=" + id +
                ", root_catalog='" + root_catalog + '\'' +
                ", metadata='" + metadata + '\'' +
                ", size=" + size +
                ", file_path=" + file_path +
                '}';
    }

    public Inode() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isIs_file() {
        return is_file;
    }

    public void setIs_file(boolean is_file) {
        this.is_file = is_file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoot_catalog() {
        return root_catalog;
    }

    public void setRoot_catalog(String root_catalog) {
        this.root_catalog = root_catalog;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
