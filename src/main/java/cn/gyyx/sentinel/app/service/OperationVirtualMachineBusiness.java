package cn.gyyx.sentinel.app.service;

import cn.gyyx.sentinel.app.domain.Result;


public interface OperationVirtualMachineBusiness {
    Result createVirtualMachine(String uri,String path);
    Result updateVirtualMachine(String uri,String path);
    Result deleteVirtualMachine(String uri,String path);
}
