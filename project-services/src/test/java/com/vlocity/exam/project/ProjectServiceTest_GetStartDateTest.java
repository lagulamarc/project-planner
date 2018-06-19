package com.vlocity.exam.project;

import java.util.Arrays;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.vlocity.exam.entities.Project;
import com.vlocity.exam.entities.Task;
import com.vlocity.exam.util.DateUtils;

public class ProjectServiceTest_GetStartDateTest extends ProjectServiceTestBase {

	/**
	 * Test get project start date
	 */
	@Test
	public void testGetProjectStartDate() {
		Project project = new Project();

		Task task1 = populateFirstTask();
		Task task2 = populateSecondTask();

		project.setTasks(Arrays.asList(task1, task2));

		ProjectService service = new ProjectService();

		Date projectStartDate = service.getProjectStartDate(project);

		Assert.assertEquals(projectStartDate, DateUtils.parseDate("2016-12-01"));
	}
}
