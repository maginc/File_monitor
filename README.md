## File monitor
 File system monitor with REST API, detects changes in added folders, <br>
information about folders is accessible thought REST API. <br>
Can be used with UNIX file system as well as Windows, <br>
All display paths is unix style.
 
#### Made with:
Spring Boot,
Elastic Search 7,
PostgreSQL, Java 8

### REST API description

### How To
1) Run elastic on your system<br>
2) Create index on first start
3) Add folder
4) Navigate thought filesystem

#### Elastic Search 7.6.1
Download ES 7.6.1 and run it.<br>
Default port is 9200

#### ``` POST /v1/index``` <br>
Will create elasticsearch index 

#### ``` POST /v1/catalog``` <br>
This will add catalog 
Requires json body to be send with name of catalog and path:<br>
``{"name":"MyCatalog", "path":"D:\\Mycatalog"}`` <br> 
Name and real name of catalog can differ.


#### ```GET /v1/catalog/all``` <br>
Get list of added catalogs

#### ```GET /v1/nav/:rootCatalogName```
Get only folders from particular root catalog

#### ```GET /v1/explorer/:path```<br>
File explorer. 
Everything after /explorer/ is treated as "parent" property from
elastic mapping.<br> 

EXAMPLE:<br>
 ```/v1/explorer/Test/``` will get all content from "Test" folder<br>
 ```/v1/explorer/Test/Sub``` will get content from "/Test/Sub" folder<br>
 ```/v1/explorer/Test/Sub2``` will get content from "/Test/Sub2" folder
 



