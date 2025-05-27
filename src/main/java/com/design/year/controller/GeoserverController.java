package com.design.year.controller;


import com.design.year.mapper.GeoserverMapper;
import com.design.year.pojo.Geoserver;
import com.design.year.result.Result;
import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.*;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSShapefileDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSGeoTIFFDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/geoserver")
public class GeoserverController {

    @Resource
    GeoserverMapper geoserverMapper;

    @PostMapping("/publishshp")
    private Result<?> publish(@RequestBody Geoserver geoserver) throws Exception {
        geoserverMapper.insert(geoserver);
        String url = "http://localhost:8080/geoserver";

        String workspace;
        String storename;
        String layername;
        String zippath;
        File zipfile;


        //与geoserver进行连接
        GeoServerRESTManager manager = new GeoServerRESTManager(new URL(url),geoserver.getUsername(),geoserver.getPassword());
        GeoServerRESTPublisher publisher = manager.getPublisher();
        GeoServerRESTReader reader = manager.getReader();
        System.out.println("连接Geoserver成功！！！！！！！");

                    workspace = geoserver.getWorkspacename();
                    System.out.println("workspace的名字是:" + workspace);

                    if (! reader.existsWorkspace(workspace)) {
                        //创建工作区
                        boolean creatWorkSpace = publisher.createWorkspace(workspace);
                        System.out.println("创建成功！！" + creatWorkSpace);
                    }else {
                        System.out.println("workspace已经存在了,workspace_name :" + workspace);
                    }
                    storename = geoserver.getStorename();
                    zipfile = new File("C:\\Users\\86136\\Desktop\\test\\China\\china.zip");
                    File filepath = new File("C:\\Users\\86136\\Desktop\\test\\China\\china.shp");
              if (!reader.existsDatastore(workspace, storename)) {

                    //创建shp数据存储
                  try {
                      String shppath = filepath.getPath();
                      String shpFilePath = String.format("file://%s", shppath);
                      URL urlshape = new URL(shpFilePath);
                      System.out.println("urlshape是：" + urlshape);
                      GSShapefileDatastoreEncoder gsShapefileDatastoreEncoder = new GSShapefileDatastoreEncoder(storename,urlshape);
                      System.out.println("gsShapefileDatastoreEncoder:"+gsShapefileDatastoreEncoder);
                      gsShapefileDatastoreEncoder.setCharset(Charset.forName("GBK"));
                      gsShapefileDatastoreEncoder.setUrl(urlshape);
                      boolean createStore = manager.getStoreManager().create(workspace,gsShapefileDatastoreEncoder);
                      System.out.println("创建数据存储成功！！！" + createStore);
                  }catch (Exception e){
                      e.printStackTrace();
                  }
                  }




              layername = "china";
                    //判断图层是否已经存在，不存在则创建并发布
        if (!reader.existsLayer(workspace, layername)) {
            try {
                GSFeatureTypeEncoder gsFeatureTypeEncoder = new GSFeatureTypeEncoder();
                gsFeatureTypeEncoder.setTitle(layername);
                gsFeatureTypeEncoder.setName(layername);
                gsFeatureTypeEncoder.setSRS(GeoServerRESTPublisher.DEFAULT_CRS);
                GSLayerEncoder gsLayerEncoder = new GSLayerEncoder();
                boolean layerpublish = publisher.publishDBLayer(workspace, storename, gsFeatureTypeEncoder, gsLayerEncoder);
                System.out.println("发布图层创建 : " + layerpublish);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            boolean shape = publisher.publishShp(workspace, storename, layername, zipfile, GeoServerRESTPublisher.DEFAULT_CRS);
            System.out.println("数据发布: " + shape);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RESTDataStore restDataStore = manager.getReader().getDatastore(workspace,storename);
        System.out.println("发布的数据是：" + restDataStore);

        return Result.success();
    }


    @PostMapping("/publishtif")
    public Result<?> publishtif (@RequestBody Geoserver geoserver) throws Exception
    {
       geoserverMapper.insert(geoserver);
        String tifZip;
        String tifFile = "C:\\Users\\86136\\Desktop\\test\\test.tif";
        String url = "http://localhost:8080/geoserver";
        String workspace;
        String storename;
        String layername;

        //与geoserver进行连接
        GeoServerRESTManager manager = new GeoServerRESTManager(new URL(url),geoserver.getUsername(),geoserver.getPassword());
        GeoServerRESTPublisher publisher = manager.getPublisher();
        GeoServerRESTReader reader = manager.getReader();
        System.out.println("连接Geoserver成功！！！！！！！");

        workspace = geoserver.getWorkspacename();
        // 检查workspace 若不存在，创建workspace
        List<String> workspaceNames = reader.getWorkspaceNames();
        if (!workspaceNames.contains(workspace)) {
            boolean publisherWorkspace = publisher.createWorkspace(workspace);
            System.out.println("create workspace_name : " + publisherWorkspace);
        } else {
            System.out.println("workspace已经存在了,workspace_name name :" + workspace);
        }
        //store是否存在，不存在则新建并发布数据。
        storename = geoserver.getStorename();
        RESTDataStoreList datastoresList = reader.getDatastores(workspace);
        List<String> datastoreNameList = datastoresList.getNames();
        boolean storeNull = !datastoreNameList.contains(storename);
        if (storeNull) {
            boolean result = publisher.publishGeoTIFF(workspace, storename, new File(tifFile));
            //converageName可以理解为图层名字 方法重载的两种方式
            // boolean result = publisher.publishGeoTIFF(workspace_name, store_name, "coverageName*", new File(file_name));
            System.out.println("数据发布是否成功：" + result);
        } else {
            System.out.println("数据已经发布过了，不能重复发布！");
        }
        RESTDataStore restStore = reader.getDatastore(workspace, storename);
        if (restStore == null) {
            GSGeoTIFFDatastoreEncoder gsGeoTIFFDatastoreEncoder = new GSGeoTIFFDatastoreEncoder(storename);
            gsGeoTIFFDatastoreEncoder.setWorkspaceName(workspace);
            gsGeoTIFFDatastoreEncoder.setUrl(new URL("file:" + tifFile));
            boolean createStore = manager.getStoreManager().create(workspace, gsGeoTIFFDatastoreEncoder);
            System.out.println("create store (TIFF文件创建状态) : " + createStore);

            boolean publish = manager.getPublisher().publishGeoTIFF(workspace, storename, new File(tifFile));
            System.out.println("publish (TIFF文件发布状态) : " + publish);

        } else {
            System.out.println("数据存储已经存在了,store:" + storename);
        }

        return  Result.success();
    }

}
