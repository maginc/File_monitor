package com.themagins.filemonitor.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.themagins.filemonitor.elastic.Document;
import com.themagins.filemonitor.elastic.ElasticIndexMapping;
import com.themagins.filemonitor.fsmonitor.FileMonitor;
import com.themagins.filemonitor.fsmonitor.FilesystemCrawler;
import com.themagins.filemonitor.persistance.Inode;
import com.themagins.filemonitor.persistance.model.Catalog;
import com.themagins.filemonitor.persistance.repository.CatalogRepository;
import com.themagins.filemonitor.persistance.repository.PathRepository;
import com.themagins.filemonitor.processor.FileProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author Andris Magins
 * @created 21/01/2020
 **/

//TODO remove @CrossOrigin before deployment
@RestController
@CrossOrigin("*")
@RequestMapping("/v1")
public class CatalogController {
    private static Logger LOG = LoggerFactory.getLogger(CatalogController.class);

    final
    Document document;
    final CatalogRepository catalogRepository;
    final PathRepository pathRepository;
    private final RestHighLevelClient client;

    public CatalogController(CatalogRepository catalogRepository, PathRepository pathRepository, RestHighLevelClient client, Document document) {
        this.catalogRepository = catalogRepository;
        this.pathRepository = pathRepository;
        this.client = client;
        this.document = document;
    }

    @GetMapping("/explorer/**")
    @ResponseBody
    public List<Inode> searchByParent(HttpServletRequest request) throws IOException {
        List<Inode> inodes = new ArrayList<>();
        String requestURL = request.getRequestURL().toString();

        String parent = requestURL.split("/explorer/")[1];


        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("parent", parent)
                .fuzziness(Fuzziness.ZERO)
                .prefixLength(2)
                .maxExpansions(10);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Inode inode = new ObjectMapper().readValue(searchHit.getSourceAsString(), Inode.class);
            inodes.add(inode);
        }
        return inodes;
    }

    @GetMapping("/nav/{rootCatalog}")
    @ResponseBody
    public List<Inode> searchFoldersByRootCatalog(@PathVariable  String rootCatalog) throws IOException {
        List<Inode> inodes = new ArrayList<>();



        QueryBuilder matchQueryBuilder = QueryBuilders.boolQuery()
                .must(termQuery("is_file", false))
                .must(termQuery("root_catalog", rootCatalog));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(10000);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Inode inode = new ObjectMapper().readValue(searchHit.getSourceAsString(), Inode.class);
            inodes.add(inode);
        }
        return inodes;
    }

    @GetMapping("/toster")
    @ResponseBody
    public String toster(){

        return "{\"userId\":1,\"id\": 1,\"title\" : \"Azazaaa lol lol\"}";
    }


    @GetMapping("/nav/tree/{rootCatalog}")
    @ResponseBody
    public  List<Inode> getStructuredFolderTreeRepresentation(String rootCatalog) throws IOException {
        List<Inode> inodes = new ArrayList<>();



        QueryBuilder matchQueryBuilder = QueryBuilders.boolQuery()
                .must(termQuery("is_file", false))
                .must(termQuery("root_catalog", rootCatalog));

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.from(0);
        sourceBuilder.size(10000);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            Inode inode = new ObjectMapper().readValue(searchHit.getSourceAsString(), Inode.class);
            inodes.add(inode);
        }
        return inodes;
    }

    //TODO take care about exception handling,
    // in this case duplicated values does not send any meaningful response to user
    @PostMapping("/index")
    @ResponseStatus(HttpStatus.CREATED)
    public void index() throws IOException {

        LOG.info("Creating Elastic Search mapping");
        CreateIndexRequest request = new CreateIndexRequest("inodes");

        request.source(ElasticIndexMapping.getStringFromFile("mapping.json"), XContentType.JSON);

        GetIndexRequest getIndexRequest = new GetIndexRequest("inodes");
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (!exists) {
            CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            System.out.println("response id: " + indexResponse.index());
        }
    }

    @PostMapping(value = "/inode", consumes = "application/json")
    public String save(@RequestBody Inode inode) throws IOException {
        IndexRequest request = new IndexRequest("inodes");
        request.id(inode.getId().toString());
        request.source(new ObjectMapper().writeValueAsString(inode), XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        System.out.println("response id: " + indexResponse.getId());
        return indexResponse.getResult().name();
    }


    @RequestMapping(
            value = "/catalog",
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String addCatalog(@RequestBody Map<String, String> payload) {
        System.out.println("Payload: " + payload.toString());

        Catalog catalog = new Catalog(payload.get("name"), payload.get("path"));
        try {
            catalogRepository.save(catalog);

            List<File> fileList = new FilesystemCrawler(payload.get("path")).getFiles();

          //  Elastic elastic = new Elastic(new Document(client));
            //elastic.indexCatalog(catalog, fileList);

            new FileProcessor(catalog, document).process(fileList);
            new Thread(new FileMonitor(catalog, 2000, document)).start();

            LOG.info("New catalog with name: " + payload.get("name") + " and path: " + payload.get("path") + " is created");

            return "{\"status\":\"DONE\", \"description\": \"New catalog with name: " + payload.get("name") + " and path: " + payload.get("path") + " is created\"}";

        } catch (DataIntegrityViolationException ex) {
            return "{\"status\":\"ERROR\", \"desription\": \"catalog name already exist\"}";
        }
    }

    @RequestMapping(
            value = "/catalog/{catalogName}",
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Catalog getCatalogStructure(@PathVariable String catalogName) {

        return catalogRepository.findByName(catalogName);

    }

    @RequestMapping(
            value = "/catalog/all",
            method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Catalog> getAddedCatalogs() {

        return catalogRepository.findAll();

    }
//    @RequestMapping(
//            value = "/catalog/path/{catalogName}",
//            method = RequestMethod.GET)
//    public DirectoryNode getPathsFromCatalog(@PathVariable String catalogName) {
//        List<String> result = pathRepository.getDisplayPaths(catalogName);
//
//        LOG.info("");
//        //System.out.println(result);
//
//        // PathTree.createDirectoryTree(result);
//
//
//        return PathTree.createDirectoryTree(new Inode());
//    }


}
