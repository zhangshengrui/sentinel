package cn.gyyx.sentinel.app.mapper.db2;

import cn.gyyx.sentinel.app.domain.AppInfo;
import cn.gyyx.sentinel.app.domain.Assets;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperationDBMapper {
    Integer addVirtualAssets(Assets assets); //添加虚资产
    Integer querySubidAppInfo(Integer mainId);//查询
    Integer addAppInfo(AppInfo appInfo); //新增应用资产
    Integer updateVirtualAssets(Assets assets); //修改资产
    Integer deleteVirtualAssets(Assets assets); //删除资产
    List<Assets> queryAssetsByGysn(String gysn); //查询gysn是否重复
}
