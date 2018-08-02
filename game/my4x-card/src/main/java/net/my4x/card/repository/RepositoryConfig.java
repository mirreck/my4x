package net.my4x.card.repository;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("net.my4x.card.repository")
@EnableTransactionManagement
@PropertySource({ "classpath:config.yaml" })
public class RepositoryConfig {

	@Autowired
	private Environment env;

	@Bean
	public DataSource testDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}

	@Bean
	public EntityManagerFactory entityManagerFactory(final DataSource dataSource) {
		final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPersistenceUnitName("javaconfigSamplePersistenceUnit");
		em.setPackagesToScan("net.my4x.card.model");
		final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		em.afterPropertiesSet();
		return em.getObject();
	}

	@Bean
	JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	// <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
	// <property name="javax.persistence.schema-generation.scripts.action" value="drop-and-create"/>
	// <property name="javax.persistence.schema-generation.scripts.create-target" value="sampleCreate.ddl"/>
	// <property name="javax.persistence.schema-generation.scripts.drop-target" value="sampleDrop.ddl"/>
	// <property name="javax.persistence.jdbc.driver" value="org.apache.derby.jdbc.EmbeddedDriver"/>
	// <property name="javax.persistence.jdbc.url" value="jdbc:derby:memory:sampleDB;create=true"/>
	// <property name="javax.persistence.sql-load-script-source" value="insert.sql"/>
	Properties additionalProperties() {
		final Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.setProperty("hibernate.hbm2ddl.import_files", "sql/insert.sql");

		properties.setProperty("javax.persistence.sql-load-script-source", "sql/insert.sql");
		return properties;
	}
}
