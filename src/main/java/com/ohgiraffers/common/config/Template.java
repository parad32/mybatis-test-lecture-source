package com.ohgiraffers.common.config;

import com.ohgiraffers.menu.model.dao.MenuMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.io.Resources;

import java.io.InputStream;

public class Template {

    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost/menudb";
    private static String USER = "ohgiraffers";
    private static String PASSWORD = "ohgiraffers";

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSqlSession() {

        if (sqlSessionFactory == null) {

            try {
                // MyBatis 설정 파일 로딩
                InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

                // SqlSessionFactory 설정
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

                // SQL 세션 구성
                Environment env = new Environment(
                        "dev",
                        new JdbcTransactionFactory(),
                        new PooledDataSource(DRIVER, URL, USER, PASSWORD)
                );

                Configuration config = new Configuration(env);
                config.getTypeAliasRegistry().registerAlias("MenuDTO", com.ohgiraffers.menu.model.dto.MenuDTO.class);
                config.addMapper(MenuMapper.class);

                // 생성된 SQL 세션 팩토리의 구성에 새로운 설정 추가
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);

                // 등록된 매퍼 확인 (디버깅용)
                System.out.println("Registered Mappers: " + config.getMapperRegistry().getMappers());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        System.out.println("sqlSessionFactory의 hashCode(): = " + sqlSessionFactory.hashCode());

        return sqlSession;
    }
}
