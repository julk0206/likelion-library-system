package com.team.five.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class SqlSessionFactoryManager {

    private static SqlSessionFactory factory;

    static {
        String path = "mybatis_config.xml";

        try {
            Reader reader = Resources.getResourceAsReader(path);
            factory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SqlSessionFactory getFactory() {
        return factory;
    }
}
