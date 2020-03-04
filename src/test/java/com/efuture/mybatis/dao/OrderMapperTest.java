package com.efuture.mybatis.dao;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.efuture.mybatis.pojo.Order;

public class OrderMapperTest {
	public OrderMapper orderMapper;

	@Before
	public void setUp() throws Exception {
		// 指定配置文件
		String resource = "mybatis-config.xml";
		// 读取配置文件
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 构建sqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 获取sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession(true);

		// 1. 映射文件的命名空间（namespace）必须是mapper接口的全路径
		// 2. 映射文件的statement的id必须和mapper接口的方法名保持一致
		// 3. Statement的resultType必须和mapper接口方法的返回类型一致
		// 4. statement的parameterType必须和mapper接口方法的参数类型一致（不一定）
		this.orderMapper = sqlSession.getMapper(OrderMapper.class);
	}

//	@Test
//	public void queryOrderWithUserByOrderNumber() throws Exception {
//	    Order order = orderMapper.queryOrderWithUserByOrderNumber("201807010001");
//	    System.out.println(order.getUser());
//	}

//	@Test
//	public void queryOrderWithUserAndDetailByOrderNumber() throws Exception {
//		Order order = orderMapper.queryOrderWithUserAndDetailByOrderNumber("201807010001");
//		System.out.println(order);
//		System.out.println(order.getUser());
//		System.out.println(order.getDetailList());
//	}
	
//	@Test
//	public void queryOrderWithUserAndDetailItemByOrderNumber() throws Exception {
//	    Order order = orderMapper.queryOrderWithUserAndDetailItemByOrderNumber("201807010001");
//	    System.out.println(order);
//	    System.out.println(order.getUser());
//	    System.out.println(order.getDetailList());
//	}
	
	@Test
	public void queryOrderWithUserAndDetailByOrderNumber() throws Exception {
		Order order = orderMapper.queryOrderAndUserByOrderNumberLazy("201807010001");
		System.out.println(order);
		System.out.println("================================");
		System.out.println(order.getUser());
	}
	
}