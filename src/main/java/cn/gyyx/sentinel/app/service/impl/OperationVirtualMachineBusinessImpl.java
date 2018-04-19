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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Result createVirtualMachine(String uri,String path) {
        Result result =  AttestationFilter.attestation(uri);
        if(!result.getCode().equals("200.0")){
            return result;
        }
        try {
            Map<String,String> map = (Map<String, String>) result.getData();
            Attestation attestation = new Attestation();
            attestation.setAuth_id(map.get("auth_id"));
            List<Attestation> list = operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setCode("401.2");
                result.setMsg("authId is illegal");
                return result;
            }
            attestation.setApp(map.get("app"));
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
            String sign = AttestationFilter.md5Encode(map,list.get(0).getAuth_key(),path);
            if(!sign.equals(map.get("sign"))){
                result.setCode("401.5");
                result.setMsg("sign error");
                return result;
            }

            //插入资产信息
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
