package com.vlocity.exam.project;

import java.util.Arrays;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.vlocity.exam.entities.Project;
import com.vlocity.exam.entities.Task;
import com.vlocity.exam.util.DateUtils;

public class ProjectServiceTest_GetEndDateTest extends ProjectServiceTestBase {

	/**
	 * Test get project end date
	 */
	@Test
	public void testGetProjectEndDate() {
		Project project = new Project();

		Task task1 = populateFirstTask();
		Task task2 = populateSecondTask();

		project.setTasks(Arrays.asList(task1, task2));

		ProjectService service = new ProjectService();

		Date projectEndDate = service.getProjectFinishDate(project);

		Assert.assertEquals(projectEndDate, DateUtils.parseDate("2018-12-01"));
	}
}
