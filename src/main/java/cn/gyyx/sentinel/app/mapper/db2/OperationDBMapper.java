package cn.gyyx.sentinel.app.mapper.db2;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationDBMapper {
    Integer addTest(String name);
}
