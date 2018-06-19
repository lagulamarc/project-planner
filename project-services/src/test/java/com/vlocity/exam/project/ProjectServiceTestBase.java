package com.vlocity.exam.project;

import static com.vlocity.exam.util.DateUtils.parseDate;

import java.util.Arrays;
import java.util.List;

import com.vlocity.exam.entities.Subtask;
import com.vlocity.exam.entities.Task;

public class ProjectServiceTestBase {

	protected Task populateFirstTask() {
		Task task = new Task();
		task.setStartDate(parseDate("2018-11-01"));
		task.setEndDate(parseDate("2018-12-01"));

		Subtask subTask1 = new Subtask();
		subTask1.setStartDate(parseDate("2018-01-01"));
		subTask1.setEndDate(parseDate("2018-03-01"));

		Subtask subTask2 = new Subtask();
		subTask2.setStartDate(parseDate("2018-06-01"));
		subTask2.setEndDate(parseDate("2018-07-01"));

		Subtask subTask3 = new Subtask();
		subTask3.setStartDate(parseDate("2017-12-01"));
		subTask3.setEndDate(parseDate("2018-02-01"));

		List<Subtask> subTasks = Arrays.asList(subTask1, subTask2, subTask3);
		task.setSubtasks(subTasks);

		return task;
	}

	protected Task populateSecondTask() {
		Task task = new Task();
		task.setStartDate(parseDate("2017-07-01"));
		task.setEndDate(parseDate("2017-09-01"));

		Subtask subTask1 = new Subtask();
		subTask1.setStartDate(parseDate("2017-01-01"));
		subTask1.setEndDate(parseDate("2017-03-01"));

		Subtask subTask2 = new Subtask();
		subTask2.setStartDate(parseDate("2017-08-01"));
		subTask2.setEndDate(parseDate("2017-09-01"));

		Subtask subTask3 = new Subtask();
		subTask3.setStartDate(parseDate("2016-12-01"));
		subTask3.setEndDate(parseDate("2017-02-01"));

		List<Subtask> subTasks = Arrays.asList(subTask1, subTask2, subTask3);
		task.setSubtasks(subTasks);

		return task;
	}
}
