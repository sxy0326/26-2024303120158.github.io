package com.design.year.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("infortable")
public class Infortable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String satellite;
    private Integer PRODUCTID;
    private Date SCENEDATE;
    private String CENTERLONGITUDE;
    private String CENTERLATITUDE;
    private String SENSORID;
    private Float AREA;
    private Float RESOLUTION;
    private String BAND1;
    private String BAND2;
    private String BAND3;
    private String BAND4;
    private String GeoModel;
    private String GeoProjection;
    private String Top_left_lon;
    private String Top_left_lat;
    private String Top_right_lon;
    private String Top_right_lat;
    private String Lower_right_lon;
    private String Lower_right_lat;
    private String Lower_left_lon;
    private String Lower_left_lat;
    private String IMAGE;
    private String region;





}
