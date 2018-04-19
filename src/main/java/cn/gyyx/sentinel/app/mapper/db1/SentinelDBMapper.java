package cn.gyyx.sentinel.app.mapper.db1;

import cn.gyyx.sentinel.app.domain.Attestation;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
@Mapper
public interface SentinelDBMapper {
    List<Attestation> queryAuthInfo(Attestation attestation);
}
