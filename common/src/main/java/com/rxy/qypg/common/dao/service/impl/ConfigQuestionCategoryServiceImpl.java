package com.rxy.qypg.common.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rxy.qypg.common.dao.entity.ConfigQuestionCategory;
import com.rxy.qypg.common.dao.entity.User;
import com.rxy.qypg.common.dao.repository.ConfigQuestionCategoryMapper;
import com.rxy.qypg.common.dao.service.IConfigQuestionCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxy.qypg.common.enums.StatusEnums;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 问题维度表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Service
public class ConfigQuestionCategoryServiceImpl extends ServiceImpl<ConfigQuestionCategoryMapper, ConfigQuestionCategory> implements IConfigQuestionCategoryService {

    public List<ConfigQuestionCategory> queryByQyType(Integer qyType) {
        LambdaQueryWrapper<ConfigQuestionCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConfigQuestionCategory::getQyType,qyType).eq(ConfigQuestionCategory::getStatus, StatusEnums.USE_ING.getStatus());
        return this.list(queryWrapper);
    }
}
