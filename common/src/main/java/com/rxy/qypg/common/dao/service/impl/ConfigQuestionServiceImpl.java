package com.rxy.qypg.common.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rxy.qypg.common.dao.entity.ConfigQuestion;
import com.rxy.qypg.common.dao.entity.User;
import com.rxy.qypg.common.dao.repository.ConfigQuestionMapper;
import com.rxy.qypg.common.dao.service.IConfigQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxy.qypg.common.enums.StatusEnums;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 问题配置表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Service
public class ConfigQuestionServiceImpl extends ServiceImpl<ConfigQuestionMapper, ConfigQuestion> implements IConfigQuestionService {

    public List<ConfigQuestion> queryChildrenListByParentId(Integer currentQuestionId) {
        LambdaQueryWrapper<ConfigQuestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConfigQuestion::getParentId,currentQuestionId).eq(ConfigQuestion::getStatus, StatusEnums.USE_ING.getStatus());
        return this.list(queryWrapper);
    }

    public ConfigQuestion queryLastOneByCategoryId(Integer categoryType) {
        LambdaQueryWrapper<ConfigQuestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConfigQuestion::getCategoryType,categoryType).eq(ConfigQuestion::getStatus, StatusEnums.USE_ING.getStatus());
        queryWrapper.orderByAsc(ConfigQuestion::getSort);
        queryWrapper.last("limit 1");
        return this.getOne(queryWrapper);
    }
}
