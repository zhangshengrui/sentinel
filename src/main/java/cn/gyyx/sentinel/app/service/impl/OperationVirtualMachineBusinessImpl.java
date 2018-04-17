package cn.gyyx.sentinel.app.service.impl;

import cn.gyyx.sentinel.app.mapper.OperationVirtualMachineMapper;
import cn.gyyx.sentinel.app.attestation.AttestationFilter;
import cn.gyyx.sentinel.app.domain.Attestation;
import cn.gyyx.sentinel.app.domain.Result;
import cn.gyyx.sentinel.app.service.OperationVirtualMachineBusiness;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OperationVirtualMachineBusinessImpl implements OperationVirtualMachineBusiness {

    private final static Logger logger  =  Logger.getLogger(OperationVirtualMachineBusinessImpl.class);

    @Autowired
    private OperationVirtualMachineMapper operationVirtualMachineMapper;

    @Override
    public Result createVirtualMachine(String uri) {
        Result result =  AttestationFilter.attestation(uri);
        //if(result.getCode() != 200.0){
        //    return result;
        //}
        try {
            Map<String,String> map = (Map<String, String>) result.getData();
            //String authId = map.get("authId");
            String authId = "111";
            if(StringUtils.isBlank(authId)){
                result.setCode(401.0);
                result.setMsg("params authId get authid fail");
                return result;
            }
            Attestation attestation = new Attestation();
            attestation.setAuthId(authId);
            List<Attestation> list = operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setCode(401.2);
                result.setMsg("authId is illegal");
                return result;
            }
            String app = map.get("app");
            if(StringUtils.isBlank(app)){
                result.setCode(401.0);
                result.setMsg("params app get authid fail");
                return result;
            }
            attestation.setApp(app);
            list =operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setCode(401.6);
                result.setMsg("authId permission denied");
                return result;
            }
            result.setCode(400.0);
            result.setMsg("success");
            return result;
        }catch (Exception e){
            logger.error("OperationVirtualMachineBusinessImpl is error");
            logger.error(e);
            result.setCode(401.0);
            result.setMsg("the system is broken");
            result.setData(e);
            return result;
        }
    }
}
