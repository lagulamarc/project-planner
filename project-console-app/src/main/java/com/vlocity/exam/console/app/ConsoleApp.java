package com.vlocity.exam.console.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.vlocity.exam.entities.Project;
import com.vlocity.exam.entities.Subtask;
import com.vlocity.exam.entities.Task;
import com.vlocity.exam.project.ProjectService;
import com.vlocity.exam.task.TaskService;

public class ConsoleApp {

	public static void main(String[] args) throws InterruptedException {
		ProjectService projectService = new ProjectService();
		TaskService taskService = new TaskService();

		Project project = populateProject();

		System.out.println("Project created...");
		System.out.println("Project Task Count: " + project.getTasks().size() + " tasks..");

		List<Task> sortedTasks = project.getTasks().stream()
				.sorted((t1, t2) -> taskService.getTaskStartDate(t1).compareTo(taskService.getTaskStartDate(t2)))
				.collect(Collectors.toList());

		for (Task task : sortedTasks) {
			System.out.println("===============================");
			System.out.println("Task Start Date: " + taskService.getTaskStartDate(task) + "..");
			System.out.println("Task End Date: " + taskService.getTaskEndDate(task) + "..");
			System.out.println("Task Duration: " + taskService.getTaskTotalDuration(task) + " days..");
			System.out.println("Task Subtask Count: " + task.getSubtasks().size() + " subtasks..");
			System.out.println("----------------------------");
			List<Subtask> sortedSubTasks = task.getSubtasks().stream()
					.sorted((t1, t2) -> t1.getStartDate().compareTo(t2.getStartDate())).collect(Collectors.toList());
			for (Subtask subtask : sortedSubTasks) {
				System.out.println("SubTask Start Date: " + taskService.getTaskStartDate(subtask) + "..");
				System.out.println("SubTask End Date: " + taskService.getTaskEndDate(subtask) + "..");
				System.out.println("SubTask Duration: " + taskService.getTaskTotalDuration(subtask) + " days..");
				System.out.println("----------------------------");
			}
		}

		System.out.println("********************************");
		System.out.println("Project Start Date: " + projectService.getProjectStartDate(project) + "..");
		System.out.println("Project End Date: " + projectService.getProjectFinishDate(project) + "..");
		System.out.println("Project Duration: " + projectService.getProjectDuration(project) + " days..");
		System.out.println("********************************");
	}

	private static Project populateProject() {
		Project project = new Project();

		// later task
		Task task1 = new Task();
		task1.setStartDate(parseDate("2018-11-01"));
		task1.setEndDate(parseDate("2018-12-01"));

		Subtask subTask11 = new Subtask();
		subTask11.setStartDate(parseDate("2018-01-01"));
		subTask11.setEndDate(parseDate("2018-03-01"));

		Subtask subTask12 = new Subtask();
		subTask12.setStartDate(parseDate("2018-06-01"));
		subTask12.setEndDate(parseDate("2018-07-01"));

		Subtask subTask13 = new Subtask();
		subTask13.setStartDate(parseDate("2017-12-01"));
		subTask13.setEndDate(parseDate("2018-02-01"));

		List<Subtask> subTasks1 = Arrays.asList(subTask11, subTask12, subTask13);
		task1.setSubtasks(subTasks1);

		// first task to be done (earlier start date)
		Task task2 = new Task();
		task2.setStartDate(parseDate("2017-07-01"));
		task2.setEndDate(parseDate("2017-09-01"));

		Subtask subTask21 = new Subtask();
		subTask21.setStartDate(parseDate("2017-01-01"));
		subTask21.setEndDate(parseDate("2017-03-01"));

		Subtask subTask22 = new Subtask();
		subTask22.setStartDate(parseDate("2017-08-01"));
		subTask22.setEndDate(parseDate("2017-09-01"));

		Subtask subTask23 = new Subtask();
		subTask23.setStartDate(parseDate("2016-12-01"));
		subTask23.setEndDate(parseDate("2017-02-01"));

		List<Subtask> subTasks2 = Arrays.asList(subTask21, subTask22, subTask23);
		task2.setSubtasks(subTasks2);

		project.setTasks(Arrays.asList(task1, task2));
		return project;
	}

	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
}
