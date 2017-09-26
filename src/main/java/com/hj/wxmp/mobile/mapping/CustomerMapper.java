package com.hj.wxmp.mobile.mapping;

import java.util.List;
import java.util.Map;

import com.hj.wxmp.mobile.entity.Customer;
import com.hj.wxmp.mobile.entity.CustomerProjs;

public interface CustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

	List<Map<String, Object>> selectByUserMessge(Map<String, Object> map);

	Integer selectByUserMessgeCount(Map<String, Object> map);

	Customer findByPhone(String phone);

	Customer selectByCustIdAndProjId(Map<String, Object> parmeter);

	CustomerProjs findGeneralMessage(Map<String, Object> map);

	List<Customer> findAll();
}