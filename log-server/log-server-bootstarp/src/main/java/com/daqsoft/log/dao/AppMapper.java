package com.daqsoft.log.dao;

import com.daqsoft.log.domain.App;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by ShawnShoper on 2017/5/24.
 *
 */
@Mapper
public interface AppMapper {
    @Select("select * from app where app_name = '${appName}'")
    App findAppByAppName(@Param("appName")String appName);
    @Select("select count(0) from app where `key` = '${key}' and `cert` = '${cert}'")
    int validateByAppIDAndCert(@Param("key")String key,@Param("cert")String cert);
    @Insert("INSERT INTO app  VALUES ('#{app_name}','#{key}','#{cert}');")
    void insert(@Param("app_name") String app_name,@Param("key") String key,@Param("cert") String cert);
}
