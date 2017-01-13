package org.inthemoon.tests.tryspringjpaplushibernate;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;

/**
 * Created by Dims on 13.01.2017.
 */
public class Main {

   @Configuration
   @Import(Service.class)
   @EnableJpaRepositories("org.inthemoon.tests.tryspringjpaplushibernate")
   public static class _Config {

      @Bean
      File programDirectory() {
         File ans = new File(".");
         return ans;
      }

      @Bean
      BasicDataSource dataSource() {
         BasicDataSource ans = new BasicDataSource();
         ans.setDriverClassName("org.h2.Driver");
         File databasePath = new File(programDirectory(), "data\\tryspringjpaplushibernate");
         ans.setUrl("jdbc:h2:file:" + databasePath.getAbsolutePath());
         return ans;
      }

      @Bean
      public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
         LocalContainerEntityManagerFactoryBean ans =
            new LocalContainerEntityManagerFactoryBean();
         ans.setDataSource(dataSource());
         ans.setJpaVendorAdapter(jpaVendorAdapter());
         ans.setPackagesToScan(getClass().getPackage().getName());


         return ans;
      }

      @Bean
      public JpaVendorAdapter jpaVendorAdapter() {
         OpenJpaVendorAdapter ans = new OpenJpaVendorAdapter();
         ans.setShowSql(false);
         ans.setGenerateDdl(true);
         ans.setDatabase(Database.H2);
         return ans;
      }

      @Bean
      public PlatformTransactionManager transactionManager() {
         JpaTransactionManager ans = new JpaTransactionManager();
         ans.setEntityManagerFactory(entityManagerFactory().getObject());
         return ans;
      }

   }

   @Component
   public static class Service {

      private final CustomerRepo customerRepo;

      @Autowired
      public Service(CustomerRepo customerRepo) {
         this.customerRepo = customerRepo;
      }

      public void doSomeOperation() {

         CustomerEntity customer = new CustomerEntity();
         customer.setId(1);
         customer.setNam("New Customer");

         customerRepo.deleteAll();

         customerRepo.save(customer);

      }
   }

   public static void main(String[] args) {

      ApplicationContext context = new AnnotationConfigApplicationContext(_Config.class);

      Service service = context.getBean( Service.class );
      service.doSomeOperation();

   }
}
