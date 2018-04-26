package cn.gyyx.sentinel.app.mapper.db2;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QueryDBMapper {
    List<Integer> queryVirtualIdByGysn(String gysn);
}
