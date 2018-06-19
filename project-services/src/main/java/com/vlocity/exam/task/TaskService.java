package com.vlocity.exam.task;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.vlocity.exam.entities.Subtask;
import com.vlocity.exam.entities.Task;
import com.vlocity.exam.util.DateUtil;

public class TaskService {

	/**
	 * Sets the start and end date of task/subtasks based on previous subtasks
	 * 
	 * @param task
	 *            - the project task
	 */
	public void arrangeTaskScheduling(Task task) {
		if (task.getSubtasks() != null && !task.getSubtasks().isEmpty()) {
			List<Subtask> sortedSubtasks = task.getSubtasks().stream()
					.sorted((t1, t2) -> t1.getStartDate().compareTo(t2.getStartDate())).collect(Collectors.toList());

			setSubtaskSchedule(sortedSubtasks);
			setTaskSchedule(task, sortedSubtasks);
		}
	}

	/**
	 * Retrieve total task duration in days. Includes subtask duration if it exists
	 * 
	 * @param task
	 *            - the project task
	 * @return duration - total task duration in days
	 */
	public long getTaskTotalDuration(Task task) {
		arrangeTaskScheduling(task);
		LocalDate taskStartDate = new java.sql.Date(getTaskStartDate(task).getTime()).toLocalDate();
		LocalDate taskEndDate = new java.sql.Date(task.getEndDate().getTime()).toLocalDate();
		return taskStartDate.until(taskEndDate, ChronoUnit.DAYS);
	}

	/**
	 * Retrieves the start date of the task. Will check subtasks first if it exists
	 * 
	 * @param task
	 *            - the project task
	 * @return startDate - the start date of the task
	 */
	public Date getTaskStartDate(Task task) {
		arrangeTaskScheduling(task);
		if (task.getSubtasks() != null && !task.getSubtasks().isEmpty()) {
			List<Date> subtaskStartDates = task.getSubtasks().stream().map(Subtask::getStartDate)
					.collect(Collectors.toList());
			return Collections.min(subtaskStartDates);
		} else {
			return task.getStartDate();
		}
	}

	/**
	 * Retrieves the final end date of the task. Will accumulate and push back
	 * general tasks end date if subtasks exists
	 * 
	 * @param task
	 *            - the project task
	 * @return endDate - the final end date of the task
	 */
	public Date getTaskEndDate(Task task) {
		arrangeTaskScheduling(task);
		return task.getEndDate();
	}

	private void setSubtaskSchedule(List<Subtask> sortedSubtasks) {
		for (int i = 0; i < sortedSubtasks.size(); i++) {
			if (i > 0 && DateUtil.hasOverlapped(sortedSubtasks.get(i - 1).getEndDate(),
					sortedSubtasks.get(i).getStartDate())) {
				long subtaskDuration = Task.getTaskDuration(sortedSubtasks.get(i));
				sortedSubtasks.get(i).setStartDate(sortedSubtasks.get(i - 1).getEndDate());
				sortedSubtasks.get(i).setEndDate(Date.from(getLocalEndDate(sortedSubtasks.get(i), subtaskDuration)
						.atStartOfDay(ZoneId.systemDefault()).toInstant()));
			}
		}
	}

	private void setTaskSchedule(Task task, List<Subtask> sortedSubtasks) {
		if (DateUtil.hasOverlapped(sortedSubtasks.get(sortedSubtasks.size() - 1).getEndDate(), task.getStartDate())) {
			long taskDuration = Task.getTaskDuration(task);
			task.setStartDate(sortedSubtasks.get(sortedSubtasks.size() - 1).getEndDate());
			task.setEndDate(
					Date.from(getLocalEndDate(task, taskDuration).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		}
	}

	private LocalDate getLocalEndDate(Task task, long duration) {
		return DateUtil.getEndDate(new java.sql.Date(task.getStartDate().getTime()).toLocalDate(), duration);
	}
}
