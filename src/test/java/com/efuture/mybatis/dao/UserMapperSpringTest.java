package com.efuture.mybatis.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.efuture.mybatis.pojo.User;

//目标：测试一下spring的bean的某些功能
@RunWith(SpringJUnit4ClassRunner.class) // junit整合spring的测试//立马开启了spring的注解
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml") // 加载核心配置文件，自动构建spring容器
public class UserMapperSpringTest {
	@Autowired
	private UserMapper userMapper;

	@Test
	public void testQueryUserByTableName() {
		List<User> userList = this.userMapper.queryUserByTableName("tb_user");
		for (User user : userList) {
			System.out.println(user);
		}
	}

	@Test
	public void testLogin() {
		System.out.println(this.userMapper.login("hj", "123456"));
	}

	@Test
	public void testQueryUserById() {
		System.out.println(this.userMapper.queryUserById("1"));
		User user = new User();
		user.setName("美女");
		user.setId("1");
		userMapper.updateUser(user);

		System.out.println(this.userMapper.queryUserById("1"));
	}
//
//	@Test
//	public void testQueryUserAll() {
//		List<User> userList = this.userMapper.queryUserAll();
//		for (User user : userList) {
//			System.out.println(user);
//		}
//	}
//
//	@Test
//	public void testInsertUser() {
//		User user = new User();
//		user.setAge(20);
//		user.setBirthday(new Date());
//		user.setName("大神");
//		user.setPassword("123456");
//		user.setSex(2);
//		user.setUserName("bigGod222");
//		this.userMapper.insertUser(user);
//		System.out.println(user.getId());
//	}
//
//	@Test
//	public void testUpdateUser() {
//		User user = new User();
//		user.setBirthday(new Date());
//		user.setName("静静");
//		user.setPassword("123456");
//		user.setSex(0);
//		user.setUserName("Jinjin");
//		user.setId("1");
//		this.userMapper.updateUser(user);
//	}
//
//	@Test
//	public void testDeleteUserById() {
//		this.userMapper.deleteUserById("1");
//	}

//	@Test
//	public void testqueryUserList() {
//		List<User> users = this.userMapper.queryUserList(null);
//		for (User user : users) {
//			System.out.println(user);
//		}
//	}
//
//	@Test
//	public void queryUserListByNameAndAge() throws Exception {
//		List<User> users = this.userMapper.queryUserListByNameAndAge("鹏程", 20);
//		for (User user : users) {
//			System.out.println(user);
//		}
//	}

//	@Test
//	public void queryUserListByNameOrAge() throws Exception {
//		List<User> users = this.userMapper.queryUserListByNameOrAge(null, 16);
//		for (User user : users) {
//			System.out.println(user);
//		}
//	}

//	@Test
//	public void queryUserListByIds() throws Exception {
//		List<User> users = this.userMapper.queryUserListByIds(new String[] { "5", "2" });
//		for (User user : users) {
//			System.out.println(user);
//		}
//	}
}