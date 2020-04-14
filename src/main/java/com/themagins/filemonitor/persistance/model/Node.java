package com.themagins.filemonitor.persistance.model;


/**
 * @author Andris Magins
 * @created 18/01/2020
 **/
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "name",
        "path",
        "size",
        "children"
})
public class Node {
    public Node() {
    }

    public Node(String type, String name, String path, long size) {
        this.type = type;
        this.name = name;
        this.path = path;
        this.size = size;
    }

    @JsonProperty("type")
    private String type;
    @JsonProperty("name")
    private String name;
    @JsonProperty("path")
    private String path;

    @JsonProperty("size")
    private long size;

    @JsonProperty("children")
    private List<Node> children = null;
    @JsonIgnore
    private Map<String, Node> additionalProperties = new HashMap<String, Node>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty("size")
    public long getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(long size) {
        this.size = size;
    }

    @JsonProperty("children")
    public List<Node> getChildren() {
        return children;
    }

    @JsonProperty("children")
    public void setChildren(List<Node> children) {
        this.children = children;
    }

    @JsonAnyGetter
    public Map<String, Node> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Node value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Node{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", children=" + children +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
