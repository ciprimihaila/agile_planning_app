package com.mycompany.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mycompany.pojo.Configuration;

@Repository
public interface ConfigurationDao {
	public List<Configuration> getAllConfigs();

	public void updateConfigurations(List<Configuration> configurations);

	public void saveConfigurations(List<Configuration> configurations);
}
