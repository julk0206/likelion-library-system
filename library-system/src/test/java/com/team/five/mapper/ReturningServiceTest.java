package com.team.five.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.team.five.config.SqlSessionFactoryManager;

public class ReturningServiceTest {

  @Test
  public void databaseConnectionTest() {

    SqlSessionFactory manager = SqlSessionFactoryManager.getFactory();
    SqlSession session = manager.openSession();
    assertNotNull(session);

  }

}
