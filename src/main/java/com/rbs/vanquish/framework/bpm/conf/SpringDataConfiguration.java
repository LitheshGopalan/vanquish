package com.rbs.vanquish.framework.bpm.conf;
import java.util.Properties;

/** --------------------------------------------------------------------------------------------------------
 * Description    : Spring Data Configuration Java class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.rbs.vanquish.framework.bpm.config.DataSourceProperties;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.rbs.vanquish.framework.bpm.entities", entityManagerFactoryRef = "vanquishEntityManager", 
transactionManagerRef = "transactionManager")
@EnableConfigurationProperties(DataSourceProperties.class)
/** --------------------------------------------------------------------------------------------------------
 * Description    : Spring Data JPA Configuration Java class for vanquish application. 
 * Author         : Lithesh Anargha 
 * Email          : Lithesh.Anargha@rbs.co.uk
 * Date           : 20/08/2018
 * Project        : Vanquish 
 * Platform       : Bankline Direct Digital
 * Organization   : Royal Bank of Scotland plc.
-------------------------------------------------------------------------------------------------------- **/
public class SpringDataConfiguration {

	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

	@Autowired
	private DataSourceProperties dataSourceProperties;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() 
    {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setDatabase(Database.ORACLE);
        return hibernateJpaVendorAdapter;
    }//eof jpaVendorAdapter
    
    @Bean(name = "vanquishDataSource", initMethod = "init", destroyMethod = "close")
    public DataSource vanquishDataSource() 
    {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(dataSourceProperties.url);
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(dataSourceProperties.password);
        mysqlXaDataSource.setUser(dataSourceProperties.username);
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("vanquishDataSource");
        return xaDataSource;
    }//eof vanquishDataSource


    @Bean(name = "vanquishEntityManager")
    public LocalContainerEntityManagerFactoryBean vanquishEntityManager() throws Throwable 
    {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(vanquishDataSource());
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setPackagesToScan("com.rbs.vanquish.framework.bpm.entities");
        entityManager.setPersistenceUnitName("vanquishPersistenceUnit");
        
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.current_session_context_class", "jta");
        properties.put("hibernate.transaction.factory_class", "org.hibernate.engine.transaction.internal.jta.CMTTransactionFactory");
        
        //properties.put("javax.persistence.transactionType", "JTA");
        //properties.put("hibernate.transaction.jta.platform","com.example.AtomikosJtaPlatfom");
 
        entityManager.setJpaProperties(properties);
        return entityManager;
    }//eof customerEntityManager

}
