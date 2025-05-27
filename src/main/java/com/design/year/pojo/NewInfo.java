package com.design.year.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.postgis.Geometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.ByteArrayInputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("gf_info_new")
public class NewInfo {
    private String productid;
    private String satid;
    private String archivedir;
    private String nrelease;
    private String starttime;
    private String geom;
    private String satsensor;
    private String rsatangle;
    private String strip;
    private String browsefile;
    private String sta;
    private String orbit;
    private String stalat;
    private String id;
    private String plevel;
    private String ysatangle;
    private String stalong;
    private String direction;
    private String imgmode;
    private String beamcode;
    private String inserttime;
    private String isrelease;
    private String endtime;
    private String psatangle;
    private String sensorid;
    private String cloudpc;
    private String staheigh;
    private String scenetime;
    private String thumbfile;
    private String imggsd;
    private String sattype;

    @Type(type = "org.hibernate.spatial.GeometryType")
    @Column(name = "geometry")
    private Geometry geometry;
    private ByteArrayInputStream img;








}
