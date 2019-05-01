package com.mycompany.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.dao.ConfigurationDao;
import com.mycompany.dao.PlanningDao;
import com.mycompany.pojo.Configuration;
import com.mycompany.pojo.Planning;
import com.mycompany.pojo.Sprint;

/**
 *
 * @author ciprian
 */
@RestController
public class AppController {

	private static final String SPRINT_NUMBER_LABEL = "Sprint Number";

	private static final String DEVELOPERS_LABEL = "Developers";

	private static final String SPRINT_LENGTH_LABEL = "Sprint Length";

	private static final String STORY_POINTS_PER_DAY_LABEL = "Story Points per Day";

	private static final String REVIEW_DAY_ALLOCATION_LABEL = "Review Day Allocation";

	private static final String USUAL_DAY_ALLOCATION_LABEL = "Usual Day Allocation";

	private static final String VELOCITY_LABEL = "Velocity";

	@Autowired
	private PlanningDao planningDao;

	@Autowired
	private ConfigurationDao configurationDao;

	private List<Configuration> initConfiguration() {
		List<Configuration> demoConfiguration = new ArrayList<>();
		demoConfiguration.add(new Configuration(VELOCITY_LABEL, 0, "velocity", 0));
		demoConfiguration.add(new Configuration(USUAL_DAY_ALLOCATION_LABEL, 1, "dayAlloc", 0));
		demoConfiguration.add(new Configuration(REVIEW_DAY_ALLOCATION_LABEL, 2, "dayReview", 0));
		demoConfiguration.add(new Configuration(STORY_POINTS_PER_DAY_LABEL, 3, "spPerDay", 0));
		demoConfiguration.add(new Configuration(SPRINT_LENGTH_LABEL, 4, "sprintLength", 0));
		demoConfiguration.add(new Configuration(DEVELOPERS_LABEL, 5, "devs", ""));
		demoConfiguration.add(new Configuration(SPRINT_NUMBER_LABEL, 6, "sprintNr", ""));
		return demoConfiguration;
	}

	@RequestMapping(value = "/save/planning", method = RequestMethod.POST)
	public String savePlanning(@RequestBody Planning planning) {
		List<Planning> existingPlanning = planningDao.findBySprintId(planning.getSprintnr());
		if (existingPlanning.size() == 0) {
			planningDao.insert(planning);
		} else {
			planningDao.update(planning);
		}
		return "/planning";
	}

	@RequestMapping(value = "/sprints", method = RequestMethod.GET)
	public List<Sprint> getSprints() {
		return planningDao.findAllSprintsId();
	}

	@RequestMapping(value = "/get/planning", method = RequestMethod.GET)
	public Planning getSprintPlanning(@RequestParam("sprintnr") String sprintNr) {
		return planningDao.findPlanningById(sprintNr);
	}

	@RequestMapping(value = "/get/configurations", method = RequestMethod.GET)
	public List<Configuration> getConfigurations() {
		List<Configuration> configurations = configurationDao.getAllConfigs();
		if (configurations.size() == 0) {
			configurations = initConfiguration();
			configurationDao.saveConfigurations(configurations);
		}
		return configurations;
	}

	@RequestMapping(value = "/save/configurations", method = RequestMethod.POST)
	public String saveConfigurations(@RequestBody List<Configuration> configurations) {
		configurationDao.updateConfigurations(configurations);
		return "/config";
	}

}
