package com.vlocity.exam.task;

import static com.vlocity.exam.util.DateUtils.parseDate;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.vlocity.exam.entities.Subtask;
import com.vlocity.exam.entities.Task;

public class TaskServiceTest_ArrangeTaskSchedulingTest {

	/**
	 * Test arrangement of subtasks should be in order of start date and should
	 * honor end date of previous/dependent subtask
	 */
	@Test
	public void testGetTaskSchedule() {
		Task generalTask = new Task();
		// Duration = 30 days
		generalTask.setStartDate(parseDate("2018-11-01"));
		generalTask.setEndDate(parseDate("2018-12-01"));

		// Duration = 59 days
		Subtask subTask1 = new Subtask();
		subTask1.setStartDate(parseDate("2018-01-01"));
		subTask1.setEndDate(parseDate("2018-03-01"));

		// Duration = 31 days
		Subtask subTask2 = new Subtask();
		subTask2.setStartDate(parseDate("2018-08-01"));
		subTask2.setEndDate(parseDate("2018-09-01"));

		// Duration = 62 days
		Subtask subTask3 = new Subtask();
		subTask3.setStartDate(parseDate("2017-12-01"));
		subTask3.setEndDate(parseDate("2018-02-01"));

		List<Subtask> subTasks = Arrays.asList(subTask1, subTask2, subTask3);

		generalTask.setSubtasks(subTasks);

		TaskService service = new TaskService();
		service.arrangeTaskScheduling(generalTask);

		// START: 2017-12-01 ; END: 2018-02-01 (Subtask 3(earliest start): 62 days)
		// START: 2018-02-01 ; END: 2018-04-01 (Subtask 1 (2nd earliest): 59 days)
		// START: 2018-08-01 ; END: 2018-09-01 (Subtask 2 (3rd earliest): 31 days)
		// START: 2018-11-01 ; END: 2018-12-01 (General Task (after subtasks): 30 days)
		Assert.assertEquals(subTask3.getStartDate(), parseDate("2017-12-01"));
		Assert.assertEquals(subTask3.getEndDate(), parseDate("2018-02-01"));

		Assert.assertEquals(subTask1.getStartDate(), parseDate("2018-02-01"));
		Assert.assertEquals(subTask1.getEndDate(), parseDate("2018-04-01"));

		Assert.assertEquals(subTask2.getStartDate(), parseDate("2018-08-01"));
		Assert.assertEquals(subTask2.getEndDate(), parseDate("2018-09-01"));

		Assert.assertEquals(generalTask.getStartDate(), parseDate("2018-11-01"));
		Assert.assertEquals(generalTask.getEndDate(), parseDate("2018-12-01"));

		// Test ordering
		List<Subtask> sortedSubtasks = generalTask.getSubtasks().stream()
				.sorted((t1, t2) -> t1.getStartDate().compareTo(t2.getStartDate())).collect(Collectors.toList());
		assertThat(sortedSubtasks, contains(subTask3, subTask1, subTask2));
	}

	/**
	 * Test arrangement of subtasks should be in order of start date and should
	 * honor end date of previous/dependent subtask. General task start date is
	 * overlapped by a subtask end date
	 */
	@Test
	public void testGetTaskScheduleGeneralTaskStartDateOverlapped() {
		Task generalTask = new Task();
		// Duration = 30 days
		generalTask.setStartDate(parseDate("2018-07-01"));
		generalTask.setEndDate(parseDate("2018-09-01"));

		// Duration = 59 days
		Subtask subTask1 = new Subtask();
		subTask1.setStartDate(parseDate("2018-01-01"));
		subTask1.setEndDate(parseDate("2018-03-01"));

		// Duration = 31 days
		Subtask subTask2 = new Subtask();
		subTask2.setStartDate(parseDate("2018-08-01"));
		subTask2.setEndDate(parseDate("2018-09-01"));

		// Duration = 62 days
		Subtask subTask3 = new Subtask();
		subTask3.setStartDate(parseDate("2017-12-01"));
		subTask3.setEndDate(parseDate("2018-02-01"));

		List<Subtask> subTasks = Arrays.asList(subTask1, subTask2, subTask3);

		generalTask.setSubtasks(subTasks);

		TaskService service = new TaskService();
		service.arrangeTaskScheduling(generalTask);

		// START: 2017-12-01 ; END: 2018-02-01 (Subtask 3(earliest start): 62 days)
		// START: 2018-02-01 ; END: 2018-04-01 (Subtask 1 (2nd earliest): 59 days)
		// START: 2018-08-01 ; END: 2018-09-01 (Subtask 2 (3rd earliest): 31 days)
		// START: 2018-09-01 ; END: 2018-11-02 (General Task (after subtasks): 61 days)
		Assert.assertEquals(subTask3.getStartDate(), parseDate("2017-12-01"));
		Assert.assertEquals(subTask3.getEndDate(), parseDate("2018-02-01"));

		Assert.assertEquals(subTask1.getStartDate(), parseDate("2018-02-01"));
		Assert.assertEquals(subTask1.getEndDate(), parseDate("2018-04-01"));

		Assert.assertEquals(subTask2.getStartDate(), parseDate("2018-08-01"));
		Assert.assertEquals(subTask2.getEndDate(), parseDate("2018-09-01"));

		Assert.assertEquals(generalTask.getStartDate(), parseDate("2018-09-01"));
		Assert.assertEquals(generalTask.getEndDate(), parseDate("2018-11-02"));

		// Test ordering
		List<Subtask> sortedSubtasks = generalTask.getSubtasks().stream()
				.sorted((t1, t2) -> t1.getStartDate().compareTo(t2.getStartDate())).collect(Collectors.toList());
		assertThat(sortedSubtasks, contains(subTask3, subTask1, subTask2));
	}
}
