package com.santander.cpe.porweb.infra.config;

import java.nio.file.Files;
import java.nio.file.Path;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/** Configuration class for reading secrets and connecting to database. */
@Configuration
@ConfigurationProperties(prefix = "apparsenal.backing-services.database")
public class DatabaseConfig {

	/** Jdbc url for database. */
	private String url;

	/** Database driver class name. */
	private String driverClassName;

	/** Database user. */
	private String username;

	/** Database pass. */
	private String password;

	/** Database poolName. */
	private String poolName;

	/** Database maxPoolSize. */
	private Integer maxPoolSize;
	
	/** Database minIdle. */
	private Integer minIdle;

	/** Database maxLifetime. */
	private Integer maxLifetime;

	/** Database validationTimeout. */
	private Integer validationTimeout;

	/** Database connectionTimeout. */
	private Integer connectionTimeout;

	/** Database idleTimeout. */
	private Integer idleTimeout;

	/** Database leakDetectionThreshold. */
	private Integer leakDetectionThreshold;

	/**
	 * Creates a DataSource Bean to connect to the database.
	 *
	 * @return Connection DataSource.
	 */
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setPoolName(poolName);
		hikariConfig.setMaximumPoolSize(maxPoolSize);
		hikariConfig.setMinimumIdle(minIdle);
		hikariConfig.setMaxLifetime(maxLifetime);
		hikariConfig.setValidationTimeout(validationTimeout);
		hikariConfig.setConnectionTimeout(connectionTimeout);
		hikariConfig.setIdleTimeout(idleTimeout);
		hikariConfig.setLeakDetectionThreshold(leakDetectionThreshold);
		return new HikariDataSource(hikariConfig);
	}

	/** Setter url. */
	public DatabaseConfig setUrl(String url) {
		this.url = url;
		return this;
	}

	/** Setter driverClassName. */
	public DatabaseConfig setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
		return this;
	}

	/** Setter username. */
	public DatabaseConfig setUsername(String username) {
		this.username = username;
		return this;
	}

	/** Setter pass. */
	public DatabaseConfig setPassword(String password) {
		this.password = password;
		return this;
	}
	
	/** Setter poolName. */
	public DatabaseConfig setPoolName(String poolName) {
		this.poolName = poolName;
		return this;
	}

	/** Setter maxPoolSize. */
	public DatabaseConfig setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
		return this;
	}
	
	/** Setter minIdle. */
    public DatabaseConfig setMinIdle(Integer minIdle) {
	  this.minIdle = minIdle;
	  return this;
    }

	/** Setter maxLifetime. */
	public DatabaseConfig setMaxLifetime(Integer maxLifetime) {
		this.maxLifetime = maxLifetime;
		return this;
	}

	/** Setter validationTimeout. */
	public DatabaseConfig setValidationTimeout(Integer validationTimeout) {
		this.validationTimeout = validationTimeout;
		return this;
	}
	
	/** Setter connectionTimeout. */
	public DatabaseConfig setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
		return this;
	}
	
	/** Setter setIdleTimeout. */
	public DatabaseConfig setIdleTimeout(Integer idleTimeout) {
		this.idleTimeout = idleTimeout;
		return this;
	}
	
	/** Setter setLeakDetectionThreshold. */
	public DatabaseConfig setLeakDetectionThreshold(Integer leakDetectionThreshold) {
		this.leakDetectionThreshold = leakDetectionThreshold;
		return this;
	}

}
