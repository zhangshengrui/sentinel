package cn.gyyx.sentinel.app.service.impl;

import cn.gyyx.sentinel.app.attestation.AttestationFilter;
import cn.gyyx.sentinel.app.domain.Assets;
import cn.gyyx.sentinel.app.domain.Attestation;
import cn.gyyx.sentinel.app.domain.Result;
import cn.gyyx.sentinel.app.mapper.db1.SentinelDBMapper;
import cn.gyyx.sentinel.app.mapper.db2.QueryDBMapper;
import cn.gyyx.sentinel.app.service.QueryVirtualMachineInfoBusiness;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cn.gyyx.sentinel.app.service.impl.OperationVirtualMachineBusinessImpl.checkModel;

@Service
public class QueryVirtualMachineInfoBusinessImpl implements QueryVirtualMachineInfoBusiness {

    private final static Logger logger  =  Logger.getLogger(QueryVirtualMachineInfoBusinessImpl.class);

    @Autowired
    private QueryDBMapper queryDBMapper;

    @Autowired
    private SentinelDBMapper operationVirtualMachineMapper;

    @Override
    public Result queryVirtualIdByGysn(String uri, String path) {
        Result result = AttestationFilter.attestation(uri);
        Assets assets = null;
        if(!result.getCode().equals("200.0")){
            return result;
        }
        try {
            Map<String,String> map = (Map<String, String>) result.getData();
            result.setData(null);
            Attestation attestation = new Attestation();
            attestation.setAuth_id(map.get("auth_id"));
            List<Attestation> list = operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setMsg("authId is illegal");
                result.setCode("401.2");
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

            //检查必要参数
            List<String> necessary = new ArrayList<>(Arrays.asList("gysn"));
            result = checkModel(map.get("param"),necessary);

            //插入资产信息
            if(!result.getCode().equals("200.0")){
                return result;
            }
            assets = (Assets)result.getData();
            result.setData(null);
            if(assets == null){
                result.setCode("401.0");
                result.setMsg("the system is broken");
                return result;
            }

            List<Integer> id = queryDBMapper.queryVirtualIdByGysn(assets.getGysn());
            if(id.size()==0){
                result.setCode("200.0");
                result.setMsg("the gysn is not exist");
                result.setData("{\"virtual_id\": \"\"}");
                return result;
            }
            if(id.size()>1){
                result.setCode("200.0");
                result.setMsg("the gysn is not unique");
                result.setData("{\"virtual_id\": \"\"}");
                return result;
            }
            result.setCode("200.0");
            result.setMsg("query virtual_id success");
            result.setData("{\"virtual_id\": \""+id.get(0)+"\"}");
            return result;
        }catch (Exception e){
            logger.error("QueryVirtualMachineInfoBusinessImpl is error");
            logger.error(e);
            result.setMsg("the system is broken");
            result.setCode("401.0");
            result.setData(e);
            return result;
        }
    }
}
