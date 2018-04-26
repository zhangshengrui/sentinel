package cn.gyyx.sentinel.app.service;

import cn.gyyx.sentinel.app.domain.Result;

public interface QueryVirtualMachineInfoBusiness {
    Result queryVirtualIdByGysn(String uri, String path);
}
