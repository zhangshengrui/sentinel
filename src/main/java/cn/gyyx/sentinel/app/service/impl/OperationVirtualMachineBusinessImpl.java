package cn.gyyx.sentinel.app.service.impl;

import cn.gyyx.sentinel.app.mapper.db1.SentinelDBMapper;
import cn.gyyx.sentinel.app.attestation.AttestationFilter;
import cn.gyyx.sentinel.app.domain.Attestation;
import cn.gyyx.sentinel.app.domain.Result;
import cn.gyyx.sentinel.app.mapper.db2.OperationDBMapper;
import cn.gyyx.sentinel.app.service.OperationVirtualMachineBusiness;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OperationVirtualMachineBusinessImpl implements OperationVirtualMachineBusiness {

    private final static Logger logger  =  Logger.getLogger(OperationVirtualMachineBusinessImpl.class);

    @Autowired
    private SentinelDBMapper operationVirtualMachineMapper;

    @Autowired
    private OperationDBMapper operationDBMapper;

    @Override
    public Result createVirtualMachine(String uri,String path) {
        operationDBMapper.addTest("111");

        Result result =  AttestationFilter.attestation(uri);
        if(!result.getCode().equals("200.0")){
            return result;
        }
        try {
            Map<String,String> map = (Map<String, String>) result.getData();
            String authId = map.get("auth_id");
            if(StringUtils.isBlank(authId)){
                result.setCode("401.0");
                result.setMsg("params authId get authid fail");
                return result;
            }
            Attestation attestation = new Attestation();
            attestation.setAuthId(authId);
            List<Attestation> list = operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setCode("401.2");
                result.setMsg("authId is illegal");
                return result;
            }
            String app = map.get("app");
            if(StringUtils.isBlank(app)){
                result.setCode("401.0");
                result.setMsg("params app get authid fail");
                return result;
            }
            attestation.setApp(app);
            list =operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setCode("401.6");
                result.setMsg("authId permission denied");
                return result;
            }
            if(list.size() != 1){
                result.setCode("401.6");
                result.setMsg("authId recognizable");
                return result;
            }
            String sign = AttestationFilter.md5Encode(map,list.get(0).getAuthKey(),path);
            if(!sign.equals(map.get("sign"))){
                result.setCode("401.5");
                result.setMsg("sign error");
                return result;
            }
            result.setCode("400.0");
            result.setMsg("success");
            return result;
        }catch (Exception e){
            logger.error("OperationVirtualMachineBusinessImpl is error");
            logger.error(e);
            result.setCode("401.0");
            result.setMsg("the system is broken");
            result.setData(e);
            return result;
        }
    }
}
