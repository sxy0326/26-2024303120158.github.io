package com.design.year.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.design.year.pojo.Cover;
import com.design.year.pojo.Infortable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface InforMapper extends BaseMapper<Infortable> {

    @Select("select * from infortable where region = #{region}")
    List<Infortable> getInfoNum(String region);
}
