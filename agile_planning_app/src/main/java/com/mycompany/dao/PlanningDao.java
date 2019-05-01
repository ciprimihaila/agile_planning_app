package com.mycompany.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mycompany.pojo.Planning;
import com.mycompany.pojo.Sprint;

@Repository
public interface PlanningDao {
	public void insert(Planning planning);

	public void update(Planning planning);

	public List<Planning> findBySprintId(String sprintId);

	public List<Sprint> findAllSprintsId();

	public Planning findPlanningById(String sprintId);
}
