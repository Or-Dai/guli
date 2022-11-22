package com.lyf.msmservice.service;

import java.util.Map;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/7/18 15:44
 * @Modified By:
 */
public interface MsmService  {
    boolean sendShortMessage(Map<String, Object> param,String phoneNum);
}
