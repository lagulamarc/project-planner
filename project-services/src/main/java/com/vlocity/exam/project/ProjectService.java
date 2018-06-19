package com.vlocity.exam.project;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.vlocity.exam.entities.Project;
import com.vlocity.exam.entities.Task;
import com.vlocity.exam.task.TaskService;

public class ProjectService {

	private TaskService taskService = new TaskService();

	/**
	 * Sets the start and end date the project based on tasks
	 * 
	 * @param project
	 *            - the project
	 */
	public void arrangeProjectScheduling(Project project) {
		List<Task> sortedTasks = project.getTasks().stream()
				.sorted((t1, t2) -> taskService.getTaskStartDate(t1).compareTo(taskService.getTaskStartDate(t2)))
				.collect(Collectors.toList());
		setProjectSchedule(project, sortedTasks);
	}

	/**
	 * Retrieve total project duration in days. Includes tasks duration in
	 * calculation
	 * 
	 * @param project
	 *            - the project
	 * @return duration - total project duration in days
	 */
	public long getProjectDuration(Project project) {
		arrangeProjectScheduling(project);
		LocalDate projectStartDate = new java.sql.Date(project.getStartDate().getTime()).toLocalDate();
		LocalDate projectEndDate = new java.sql.Date(project.getEndDate().getTime()).toLocalDate();
		return projectStartDate.until(projectEndDate, ChronoUnit.DAYS);
	}

	/**
	 * Retrieves the start date of the project based on tasks
	 * 
	 * @param project
	 *            - the project
	 * @return startDate - the start date of the project
	 */
	public Date getProjectStartDate(Project project) {
		arrangeProjectScheduling(project);
		return project.getStartDate();
	}

	/**
	 * Retrieves the final end date of the project. Will accumulate and push back
	 * project end date based on existing tasks (and subtasks)
	 * 
	 * @param project
	 *            - the project
	 * @return endDate - the end date of the project
	 */
	public Date getProjectFinishDate(Project project) {
		arrangeProjectScheduling(project);
		return project.getEndDate();
	}

	private void setProjectSchedule(Project project, List<Task> sortedTasks) {
		List<Date> taskStartDate = sortedTasks.stream().map(task -> taskService.getTaskStartDate(task))
				.collect(Collectors.toList());
		List<Date> taskEndDate = sortedTasks.stream().map(task -> taskService.getTaskEndDate(task))
				.collect(Collectors.toList());
		project.setStartDate(Collections.min(taskStartDate));
		project.setEndDate(Collections.max(taskEndDate));
	}
}
