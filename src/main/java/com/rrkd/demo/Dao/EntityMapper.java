package com.rrkd.demo.Dao;

import com.rrkd.demo.Entity.EntityText;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */
@Mapper
@Repository
public interface EntityMapper {

    @Select("select * from test")
    public List<EntityText> findList();

    @Insert("insert into test(cityname,stationnum,pollutantcode,pollutantnum) values(#{cityname},#{stationnum},#{pollutantcode},#{pollutantnum})")
    public void save(EntityText entityText);

    @Delete("delete from test where cityname = #{0} and stationnum = #{1}")
    public void deleteEntityText(String cityname, int stationnum);

    @SuppressWarnings("rawtypes")
    @Select("exec yy #{cityname},#{stationnum},#{pollutantcode},#{pollutantnum}")
    @Options(statementType = StatementType.CALLABLE)
    HashMap callGenCiPropertyValue(EntityText entityText);

}
