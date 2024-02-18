package urbanjungletech.hardwareservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.service.datasource.implementation.MultiTenantDataSourceImpl;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAspectJAutoProxy
public class DatabaseConfiguration {
    @Bean("dataSourceId")
    public ThreadLocal<DatabaseConnectionDetails> dataSourceId() {
        return new ThreadLocal<>();
    }

    @Bean("targetDataSources")
    Map<Object, Object> targetDataSources() {
        return new HashMap<>();
    }

    @Bean("defaultDataSource")
    public DataSource defaultDataSource(DataSourceProperties dataSourceProperties) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        DataSource result = dataSourceBuilder.url(dataSourceProperties.getUrl())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .driverClassName(dataSourceProperties.getDriverClassName())
                .build();
        return result;
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder ,
                                                                           MultiTenantDataSourceImpl multiTenantDataSourceImpl,
                                                                           JpaProperties jpaProperties) {
        LocalContainerEntityManagerFactoryBean result = entityManagerFactoryBuilder
                .dataSource(multiTenantDataSourceImpl)
                .packages("urbanjungletech")
                .properties(jpaProperties.getProperties())
                .build();
        result.setDataSource(multiTenantDataSourceImpl);
        return result;

    }

    @Bean("transactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean  entityManagerFactory) {

        JpaTransactionManager result = new JpaTransactionManager(entityManagerFactory.getObject());
        return result;
    }
}
