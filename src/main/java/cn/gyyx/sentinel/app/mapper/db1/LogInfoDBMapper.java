package cn.gyyx.sentinel.app.mapper.db1;

import cn.gyyx.sentinel.app.domain.LogInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogInfoDBMapper {
    Integer insertLogInfo(LogInfo logInfo);
}
