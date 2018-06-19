package com.vlocity.exam.task;

import static com.vlocity.exam.util.DateUtils.parseDate;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.vlocity.exam.entities.Subtask;
import com.vlocity.exam.entities.Task;

public class TaskServiceTest_GetTotalDurationTest {

	/**
	 * Test get total duration of general task in days
	 */
	@Test
	public void testGetTaskTotalDuration() {
		Task generalTask = new Task();
		generalTask.setStartDate(parseDate("2018-11-01"));
		generalTask.setEndDate(parseDate("2018-12-01"));

		Subtask subTask1 = new Subtask();
		subTask1.setStartDate(parseDate("2018-01-01"));
		subTask1.setEndDate(parseDate("2018-03-01"));

		Subtask subTask2 = new Subtask();
		subTask2.setStartDate(parseDate("2018-04-01"));
		subTask2.setEndDate(parseDate("2018-05-01"));

		Subtask subTask3 = new Subtask();
		subTask3.setStartDate(parseDate("2017-12-01"));
		subTask3.setEndDate(parseDate("2018-02-01"));

		List<Subtask> subTasks = Arrays.asList(subTask1, subTask2, subTask3);

		generalTask.setSubtasks(subTasks);

		TaskService service = new TaskService();
		long totalDuration = service.getTaskTotalDuration(generalTask);

		// 2018-12-01 = 1 year/365 days from 2017-12-01
		Assert.assertEquals(totalDuration, 365L);
	}

	/**
	 * Test get total duration of general task in days. General task start date
	 * overlapped by subtask end date
	 */
	@Test
	public void testGetTaskTotalDurationGeneralTaskOverlapped() {
		Task generalTask = new Task();
		// Duration = 59 days
		generalTask.setStartDate(parseDate("2018-01-01"));
		generalTask.setEndDate(parseDate("2018-03-01"));

		// Duration = 59 days
		Subtask subTask1 = new Subtask();
		subTask1.setStartDate(parseDate("2018-01-01"));
		subTask1.setEndDate(parseDate("2018-03-01"));

		// Duration = 30 days
		Subtask subTask2 = new Subtask();
		subTask2.setStartDate(parseDate("2018-04-01"));
		subTask2.setEndDate(parseDate("2018-05-01"));

		// Duration = 62 days
		Subtask subTask3 = new Subtask();
		subTask3.setStartDate(parseDate("2017-12-01"));
		subTask3.setEndDate(parseDate("2018-02-01"));

		List<Subtask> subTasks = Arrays.asList(subTask1, subTask2, subTask3);

		generalTask.setSubtasks(subTasks);

		TaskService service = new TaskService();
		long totalDuration = service.getTaskTotalDuration(generalTask);

		Assert.assertEquals(totalDuration, 210L);
	}
}
