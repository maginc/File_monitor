package com.themagins.filemonitor.persistance.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Andris Magins
 * @created 15/01/2020
 **/
@Entity
@Table(name = "inode")
public class Inode implements Serializable {
    public Inode() {
    }



    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "analyzed_at")
    @CreatedDate
    private Date analyzedAt = new Timestamp(System.currentTimeMillis());


    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "path_id")
    private Path path;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "metadata_id")
    private Metadata metadata;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "parent")
    private String parent;

    @Column(name = "name")
    private String name;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "is_file")
    private boolean isFile;

    @Column(name = "root_folder_name")
    private String root_folder_name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAnalyzedAt() {
        return analyzedAt;
    }

    public void setAnalyzedAt(Date analyzedAt) {
        this.analyzedAt = analyzedAt;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public String getRoot_folder_name() {
        return root_folder_name;
    }

    public void setRoot_folder_name(String root_folder_name) {
        this.root_folder_name = root_folder_name;
    }

    @Override
    public String toString() {
        return "Inode{" +
                "id=" + id +
                ", analyzedAt=" + analyzedAt +
                ", path=" + path +
                ", metadata=" + metadata +
                ", fileType='" + fileType + '\'' +
                ", parent='" + parent + '\'' +
                ", name='" + name + '\'' +
                ", fileSize=" + fileSize +
                ", isFile=" + isFile +
                ", root_folder_name='" + root_folder_name + '\'' +
                '}';
    }
}
