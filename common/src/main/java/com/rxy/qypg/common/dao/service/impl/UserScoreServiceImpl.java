package com.rxy.qypg.common.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rxy.qypg.common.dao.entity.ConfigQuestion;
import com.rxy.qypg.common.dao.entity.UserScore;
import com.rxy.qypg.common.dao.repository.UserScoreMapper;
import com.rxy.qypg.common.dao.service.IUserScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rxy.qypg.common.enums.StatusEnums;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户得分表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Service
public class UserScoreServiceImpl extends ServiceImpl<UserScoreMapper, UserScore> implements IUserScoreService {

    public List<UserScore> getListByBatchNo(String batchNo) {
        LambdaQueryWrapper<UserScore> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserScore::getBatchNo,batchNo);
        return this.list(queryWrapper);
    }
}
