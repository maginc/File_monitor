package com.themagins.filemonitor.utilities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Andris Magins
 * @created 22-Jan-20
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "filePath",
        "children",
        "parent"
})
public class DirectoryNode implements Serializable {


    @JsonProperty("children")
    private final Set<DirectoryNode> children = new LinkedHashSet<>();

    @JsonProperty("name")
    private final String name;

    @JsonProperty("filePath")
    private final String filePath;


    @JsonIgnore
    @JsonProperty("parent")
    private final DirectoryNode parent;

    public DirectoryNode(final DirectoryNode parent, final String name, final String filePath) {
        this.parent = parent;
        if (this.parent != null) {
            this.parent.children.add(this);
        }

        this.filePath = filePath;
        this.name = name;
    }


    @JsonProperty("filePath")
    public String getfilePath() {
        return filePath;
    }


    @JsonProperty("children")
    public Set<DirectoryNode> getChildren() {
        return this.children;
    }


    @JsonProperty("name")
    public String getname() {
        return this.name;
    }

    @JsonIgnore
    @JsonProperty("parent")
    public DirectoryNode getParent() {
        return this.parent;
    }

    public int getFileCount() {
        int fileCount = this.isFile() ? 1 : 0;
        for (final DirectoryNode child : this.children) {
            fileCount += child.getFileCount();
        }

        return fileCount;
    }

    public boolean isFile() {
        return this.children.isEmpty();
    }

    @JsonIgnore
    public DirectoryNode getRoot() {
        return this.parent == null ? this : this.parent.getRoot();
    }

    @JsonIgnore
    public void merge(final DirectoryNode that) {
        if (!this.name.equals(that.name)) {
            return;
        } else if (this.children.isEmpty()) {
            this.children.addAll(that.children);
            return;
        }

        final DirectoryNode[] thisChildren = this.children
                .toArray(new DirectoryNode[0]);
        for (final DirectoryNode thisChild : thisChildren) {
            for (final DirectoryNode thatChild : that.children) {
                if (thisChild.name.equals(thatChild.name)) {
                    thisChild.merge(thatChild);
                } else this.children.add(thatChild);
            }
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DirectoryNode that = (DirectoryNode) o;
        return Objects.equals(this.name, that.name)
                && Objects.equals(this.parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.parent);
    }


    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + this.name +
                "\", \"filePath\":\"" + this.filePath +
                "\", \"children\":" + this.children +
                "}";
    }
}