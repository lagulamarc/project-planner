package com.vlocity.exam.task;

import static com.vlocity.exam.util.DateUtils.parseDate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.vlocity.exam.entities.Subtask;
import com.vlocity.exam.entities.Task;

public class TaskServiceTest_GetEndDateTest {

	/**
	 * Test get final task (w/ subtasks) end date
	 */
	@Test
	public void testGetTaskEndDate() {
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

		Date generalTaskEndDate = service.getTaskEndDate(generalTask);
		Date subTask1EndDate = service.getTaskEndDate(subTask1);
		Date subTask2EndDate = service.getTaskEndDate(subTask2);
		Date subTask3EndDate = service.getTaskEndDate(subTask3);

		Assert.assertEquals(subTask1EndDate, subTask1.getEndDate());
		Assert.assertEquals(subTask2EndDate, subTask2.getEndDate());
		Assert.assertEquals(subTask3EndDate, subTask3.getEndDate());

		// START: 2017-12-01 ; END: 2018-02-01 (Subtask 3(earliest start): 62 days)
		// START: 2018-02-01 ; END: 2018-04-01 (Subtask 1 (2nd earliest): 59 days)
		// START: 2018-08-01 ; END: 2018-09-01 (Subtask 2 (3rd earliest): 30 days)
		// START: 2018-11-01 ; END: 2018-12-01 (General Task (after subtasks): 30 days)
		Assert.assertEquals(generalTaskEndDate, generalTask.getEndDate());
	}
}
