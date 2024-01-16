package com.myWeb.www.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@MapperScan(basePackages = {"com.myWeb.www.repository"})	//mapper경로 설정
@ComponentScan(basePackages = {"com.myWeb.www.service"})	//service경로 설정
@EnableTransactionManagement	//트랜잭션 허용 어노테이션
@EnableScheduling	//스케쥴링 허용 어노테이션
@Configuration		//설정파일 명시 어노테이션
public class RootConfig {
	//DB설정부분
	//hikariCP 사용	/	log4jdbc-log4j2 사용
	
	@Autowired
	ApplicationContext applicationContext;	//mapper 위치 세팅
	
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		//log4jdbc
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");	//log4jdbc.log4j2.properties에서 설정
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/mywebdb");
		hikariConfig.setUsername("mywebUser");
		hikariConfig.setPassword("mysql");
		
		hikariConfig.setMaximumPoolSize(5);	//최대 커넥션 개수(웹에서 DB나 서버로의 최대 연결 개수(비용감소 및 성능향상))
		hikariConfig.setMinimumIdle(5);	//최소 유휴(idle) 커넥션 개수(위의 값과 같은 값으로 설정(필수))
		
		hikariConfig.setConnectionTestQuery("SELECT NOW()");	//test쿼리
		hikariConfig.setPoolName("springHikariCP");
		
		//추가 설정
		//cachePrepStmts : cache 사용 여부 설정(속도향상)
		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		//mysql 드라이버가 연결당 cache statements의 수에 관한 설정 250 ~ 500 사이 권장
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", 250);
		//connection당 캐싱할 preparedStatements의 개수 지정 옵션 : default 256
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "true");	//기본값으로 설정
		//mysql 서버에서 최신 이슈가 있을 경우, 지원받는 설정
		hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
		
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		
		return hikariDataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlFactoryBean = new SqlSessionFactoryBean();
		
		sqlFactoryBean.setDataSource(dataSource());
		sqlFactoryBean.setMapperLocations(
				applicationContext.getResources("classpath:/mappers/*.xml"));
		
		sqlFactoryBean.setConfigLocation(
				applicationContext.getResource("classpath:/MybatisConfig.xml"));
		
		return sqlFactoryBean.getObject();
	}
	
	//트랜젝션 매니저 빈 설정
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
