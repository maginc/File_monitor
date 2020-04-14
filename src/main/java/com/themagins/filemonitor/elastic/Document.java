package com.themagins.filemonitor.elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.themagins.filemonitor.persistance.Inode;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Andris Magins
 * @created 28-Mar-20
 **/
@Component
public class Document implements DocumentOperations{

    final
    RestHighLevelClient client;

    public Document(RestHighLevelClient client) {
        this.client = client;
    }

    @Override
    public void save(Inode inode) throws IOException {
            IndexRequest request = new IndexRequest("inodes");

            request.id(inode.getId().toString());
            request.source(new ObjectMapper().writeValueAsString(inode), XContentType.JSON);
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println("response id: "+indexResponse.getId());

    }

    @Override
    public void delete(Inode inode ) {

    }
}
