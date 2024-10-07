package lk.ijse.posbackend.Config;

import jakarta.persistence.EntityManagerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
@Configuration
@EnableJpaRepositories(basePackages = "lk.ijse.posbackend.Dao")
@EnableTransactionManagement
@ComponentScan(basePackages = "lk.ijse.posbackend")

public class WebAppRootConfig {
    //configure connection pool and manage database connection
    @Bean
    public DataSource dataSource() {
        var driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/PosWithSpring?createDatabaseIfNotExist=true");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("Ijse@1234");
        return driverManagerDataSource;


    }

    // Manages the JPA entity lifecycle and connects to the database via JPA.
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("lk.ijse.posbackend.Entity");
        factory.setDataSource(dataSource());
        return factory;
    }

    // Manages transaction boundaries, ensuring data integrity.
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
