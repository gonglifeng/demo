package com.efuture.mybatis.dao;

import org.apache.ibatis.annotations.Param;

import com.efuture.mybatis.pojo.Order;

public interface OrderMapper {
	Order queryOrderWithUserByOrderNumber(@Param("number") String number);

	Order queryOrderWithUserAndDetailByOrderNumber(@Param("number") String number);

	Order queryOrderWithUserAndDetailItemByOrderNumber(@Param("number") String number);

	Order queryOrderAndUserByOrderNumberLazy(@Param("number") String number);

}
