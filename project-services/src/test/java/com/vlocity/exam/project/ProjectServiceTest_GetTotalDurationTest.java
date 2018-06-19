package com.vlocity.exam.project;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.vlocity.exam.entities.Project;
import com.vlocity.exam.entities.Task;

public class ProjectServiceTest_GetTotalDurationTest extends ProjectServiceTestBase {

	/**
	 * Test get total duration of the project
	 */
	@Test
	public void testGetProjectDuration() {
		Project project = new Project();

		Task task1 = populateFirstTask();
		Task task2 = populateSecondTask();

		project.setTasks(Arrays.asList(task1, task2));

		ProjectService service = new ProjectService();

		long projectDuration = service.getProjectDuration(project);

		Assert.assertEquals(projectDuration, 730L);
	}
}
