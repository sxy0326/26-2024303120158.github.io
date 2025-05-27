package com.design.year.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("geoserver")
public class Geoserver {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String storename;

    private String username;

    private String password;

    private String workspacename;

    private String filepath;

    private String layername;
}
