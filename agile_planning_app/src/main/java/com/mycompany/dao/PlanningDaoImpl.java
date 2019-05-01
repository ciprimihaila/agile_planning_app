package com.mycompany.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mycompany.pojo.Planning;
import com.mycompany.pojo.Sprint;

@Repository
public class PlanningDaoImpl implements PlanningDao {

	private static final String SPRINT_PREFIX = "Sprint ";
	@Autowired
	private MongoOperations mongoOp;

	@Override
	public void insert(Planning planning) {
		mongoOp.insert(planning);
	}

	@Override
	public void update(Planning planning) {
		Query query = new Query();
		query.addCriteria(Criteria.where("sprintnr").is(planning.getSprintnr()));
		Update update = new Update();
		update.set("spadded", planning.getSpadded());
		update.set("sdate", planning.getSdate());
		update.set("edate", planning.getEdate());
		update.set("devs", planning.getDevs());
		update.set("cols", planning.getCols());
		update.set("rows", planning.getRows());
		update.set("wdays", planning.getWdays());
		update.set("spoints", planning.getSpoints());
		update.set("velocity", planning.getVelocity());
		update.set("spconsumed", planning.getSpconsumed());
		update.set("spremain", planning.getSpremain());

		mongoOp.updateFirst(query, update, Planning.class);

	}

	@Override
	public List<Planning> findBySprintId(String sprintId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("sprintnr").is(sprintId));
		return mongoOp.find(query, Planning.class);
	}

	@Override
	public List<Sprint> findAllSprintsId() {
		Query q = new Query().with(new Sort(Sort.Direction.ASC, "sprintnr"));
		List<Planning> allPlannings = mongoOp.find(q, Planning.class);
		List<Sprint> sprints = new ArrayList<>();
		for (int i = 0; i < allPlannings.size(); i++) {
			Planning p = allPlannings.get(i);
			sprints.add(new Sprint(p.getSprintnr(), SPRINT_PREFIX + p.getSprintnr()));
		}
		return sprints;
	}

	@Override
	public Planning findPlanningById(String sprintId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("sprintnr").is(sprintId));
		return mongoOp.findOne(query, Planning.class);
	}

}
