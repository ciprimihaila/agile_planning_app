package com.mycompany.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mycompany.pojo.Configuration;

@Repository
public class ConfigurationDaoImpl implements ConfigurationDao {

	@Autowired
	private MongoOperations mongoOp;

	@Override
	public List<Configuration> getAllConfigs() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC, "index"));
		return mongoOp.find(query, Configuration.class);
	}

	@Override
	public void updateConfigurations(List<Configuration> configurations) {
		for (Configuration cons : configurations) {
			Query query = new Query();
			query.addCriteria(Criteria.where("index").is(cons.getIndex()));
			Update update = new Update();
			update.set("label", cons.getLabel());
			update.set("name", cons.getName());
			update.set("value", cons.getValue());
			mongoOp.updateFirst(query, update, Configuration.class);
		}

	}

	@Override
	public void saveConfigurations(List<Configuration> configurations) {
		mongoOp.insertAll(configurations);
	}

}
