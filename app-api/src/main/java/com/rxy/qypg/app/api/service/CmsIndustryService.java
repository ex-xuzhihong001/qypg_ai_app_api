package com.rxy.qypg.app.api.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rxy.qypg.app.api.domain.request.IndustryListReq;
import com.rxy.qypg.app.api.domain.response.IndustryVo;
import com.rxy.qypg.common.dao.entity.ConfigIndustry;
import com.rxy.qypg.common.dao.entity.SmsPgGoods;
import com.rxy.qypg.common.dao.service.IConfigIndustryService;
import com.rxy.qypg.common.dao.service.impl.SmsPgGoodsServiceImpl;
import com.rxy.qypg.common.enums.IndustryTypeEnums;
import com.rxy.qypg.common.enums.StatusEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CmsIndustryService {

    Logger logger = LoggerFactory.getLogger(CmsIndustryService.class);

    @Resource
    private IConfigIndustryService iConfigIndustryService;
    @Resource
    private SmsPgGoodsServiceImpl smsPgGoodsServiceImpl;

    public List<IndustryVo> getIndustryListByType(IndustryListReq req) {
        List<IndustryVo> res = new ArrayList<>();
        IndustryTypeEnums enums = IndustryTypeEnums.getEnums(req.getType());
        if(enums==null){
            for (IndustryTypeEnums value : IndustryTypeEnums.values()) {
                List<ConfigIndustry> list = getTreeVoListByType(value.getType());
                IndustryVo build = IndustryVo.builder().name(value.getName()).type(value.getType()).list(list).build();
                res.add(build);
            }
        }else {
            List<ConfigIndustry> list = getTreeVoListByType(enums.getType());
            IndustryVo build = IndustryVo.builder().name(enums.getName()).type(enums.getType()).list(list).build();
            res.add(build);
        }
        return res;
    }

    private List<ConfigIndustry> getTreeVoListByType(Integer type) {
        LambdaQueryWrapper<ConfigIndustry> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConfigIndustry::getType,type).eq(ConfigIndustry::getStatus, StatusEnums.USE_ING.getStatus());
        List<ConfigIndustry> list = iConfigIndustryService.list(queryWrapper);
        return  list.stream().filter(item -> item.getParentId() == 0).peek(item -> item.setChildrenList(getChildren(item, list))).collect(Collectors.toList());
    }

    private static List<ConfigIndustry> getChildren(ConfigIndustry treeEntity, List<ConfigIndustry> treeEntityList) {
        return treeEntityList.stream().filter(item -> item.getParentId().equals(treeEntity.getId())).peek(item -> item.setChildrenList(getChildren(item, treeEntityList))).collect(Collectors.toList());
    }

    public List<SmsPgGoods> getAiGoods() {
        LambdaQueryWrapper<SmsPgGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SmsPgGoods::getStatus,StatusEnums.USE_ING.getStatus());
        return smsPgGoodsServiceImpl.list(queryWrapper);
    }
}
