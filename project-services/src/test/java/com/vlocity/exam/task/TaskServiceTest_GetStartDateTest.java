package com.vlocity.exam.task;

import static com.vlocity.exam.util.DateUtils.parseDate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.vlocity.exam.entities.Subtask;
import com.vlocity.exam.entities.Task;

public class TaskServiceTest_GetStartDateTest {

	/**
	 * Test get earliest task start date among subtasks
	 */
	@Test
	public void testGetTaskStartDate() {
		Task generalTask = new Task();
		generalTask.setStartDate(parseDate("2018-11-01"));
		generalTask.setEndDate(parseDate("2018-12-01"));

		Subtask subTask1 = new Subtask();
		subTask1.setStartDate(parseDate("2018-01-01"));
		subTask1.setEndDate(parseDate("2018-03-01"));

		Subtask subTask2 = new Subtask();
		subTask2.setStartDate(parseDate("2018-08-01"));
		subTask2.setEndDate(parseDate("2018-09-01"));

		Subtask subTask3 = new Subtask();
		subTask3.setStartDate(parseDate("2017-12-01"));
		subTask3.setEndDate(parseDate("2018-02-01"));

		List<Subtask> subTasks = Arrays.asList(subTask1, subTask2, subTask3);

		generalTask.setSubtasks(subTasks);

		TaskService service = new TaskService();
		Date generalTaskStartDate = service.getTaskStartDate(generalTask);
		Date subTask1StartDate = service.getTaskStartDate(subTask1);
		Date subTask2StartDate = service.getTaskStartDate(subTask2);
		Date subTask3StartDate = service.getTaskStartDate(subTask3);

		Assert.assertEquals(subTask1StartDate, subTask1.getStartDate());
		Assert.assertEquals(subTask2StartDate, subTask2.getStartDate());
		Assert.assertEquals(subTask3StartDate, subTask3.getStartDate());

		// General task start date should be earliest among subtasks start date
		Assert.assertEquals(generalTaskStartDate, subTask3.getStartDate());
	}

	/**
	 * Test get earliest task start date, no subtasks
	 */
	@Test
	public void testGetTaskStartDateNoSubtask() {
		Task generalTask = new Task();
		generalTask.setStartDate(parseDate("2018-11-01"));
		generalTask.setEndDate(parseDate("2018-12-01"));

		TaskService service = new TaskService();
		Date generalTaskStartDate = service.getTaskStartDate(generalTask);

		// General task start date should be earliest among subtasks start date
		Assert.assertEquals(generalTaskStartDate, generalTask.getStartDate());
	}
}
