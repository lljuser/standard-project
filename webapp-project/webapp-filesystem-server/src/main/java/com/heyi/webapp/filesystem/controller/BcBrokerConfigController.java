package com.heyi.webapp.filesystem.controller;

import com.heyi.core.common.ApiResponse;
import com.heyi.core.filestorage.entity.BcBrokerConfig;
import com.heyi.core.filestorage.service.BcBrokerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/config")
public class BcBrokerConfigController {

    @Autowired
    private BcBrokerConfigService bcBrokerConfigService;
    // 保存节点配置
    @PostMapping(value = "/save")
    public Object saveBcBrokerConfig(@RequestBody BcBrokerConfig bcBrokerConfig) {
        return ApiResponse.success(bcBrokerConfigService.saveBcBrokerConfig(bcBrokerConfig));
    }

    // 删除节点配置
    @GetMapping(value = "/delete/{id}")
    public ApiResponse<Boolean> deleteBcBrokerConfig(@PathVariable Integer id) {
        Boolean aBoolean = bcBrokerConfigService.deleteBcBrokerConfig(id);
        if(aBoolean){
            return ApiResponse.success(true);
        }else {
            return ApiResponse.error("删除失败");
        }
    }

    // 修改节点配置
    @PostMapping(value = "/update")
    public Object updateBcBrokerConfig(@RequestBody BcBrokerConfig bcBrokerConfig) {
        Object o = bcBrokerConfigService.updateBcBrokerConfig(bcBrokerConfig);
        if(o.equals(0)){
            return  ApiResponse.error("修改失败");
        }else {
            return ApiResponse.success("修改成功" );
        }
    }

    // 查看节点配置
    @GetMapping(value = "/select/{identityCode},{type}")
    public ApiResponse<List<BcBrokerConfig>> selectBcBrokerConfig(@PathVariable String identityCode, @PathVariable short type) {
        return ApiResponse.success(bcBrokerConfigService.selectBcBrokerConfig(identityCode, type));
    }

}
