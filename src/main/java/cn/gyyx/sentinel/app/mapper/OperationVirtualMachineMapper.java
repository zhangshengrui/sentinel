package cn.gyyx.sentinel.app.mapper;

import cn.gyyx.sentinel.app.domain.Attestation;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
@Mapper
public interface OperationVirtualMachineMapper {
    List<Attestation> queryAuthInfo(Attestation attestation);
}
