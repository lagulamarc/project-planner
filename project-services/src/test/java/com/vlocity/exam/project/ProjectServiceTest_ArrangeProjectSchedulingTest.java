package com.vlocity.exam.project;

import static com.vlocity.exam.util.DateUtils.parseDate;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.vlocity.exam.entities.Project;
import com.vlocity.exam.entities.Task;

public class ProjectServiceTest_ArrangeProjectSchedulingTest extends ProjectServiceTestBase {

	/**
	 * Test arrangement of project tasks should be in order of start date and should
	 * honor end date of previous/dependent task
	 */
	@Test
	public void testGetProjectSchedule() {
		Project project = new Project();

		Task task1 = populateFirstTask();
		Task task2 = populateSecondTask();

		project.setTasks(Arrays.asList(task1, task2));

		ProjectService service = new ProjectService();
		service.arrangeProjectScheduling(project);

		Assert.assertEquals(parseDate("2016-12-01"), project.getStartDate());
		Assert.assertEquals(parseDate("2018-12-01"), project.getEndDate());

		// Test ordering
		List<Task> sortedTasks = project.getTasks().stream()
				.sorted((t1, t2) -> t1.getStartDate().compareTo(t2.getStartDate())).collect(Collectors.toList());
		assertThat(sortedTasks, contains(task2, task1));
	}
}
