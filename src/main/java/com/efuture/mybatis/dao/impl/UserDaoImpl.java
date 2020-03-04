package com.efuture.mybatis.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.efuture.mybatis.dao.UserDao;
import com.efuture.mybatis.pojo.User;

public class UserDaoImpl implements UserDao {
	public SqlSession sqlSession;

	public UserDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public User queryUserById(String id) {
		return this.sqlSession.selectOne("UserDao.queryUserById", id);
	}

	public List<User> queryUserAll() {
		return this.sqlSession.selectList("UserDao.queryUserAll");
	}

	public void insertUser(User user) {
		this.sqlSession.insert("UserDao.insertUser", user);
	}

	public void updateUser(User user) {
		this.sqlSession.update("UserDao.updateUser", user);
	}

	public void deleteUser(String id) {
		this.sqlSession.delete("UserDao.deleteUser", id);
	}

}