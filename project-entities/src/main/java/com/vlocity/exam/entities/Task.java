package com.vlocity.exam.entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class Task {
	private Date startDate;
	private Date endDate;
	private List<Subtask> subtasks;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Subtask> getSubtasks() {
		return subtasks;
	}

	public void setSubtasks(List<Subtask> subtasks) {
		this.subtasks = subtasks;
	}

	// Get task/subtask duration in days
	public static long getTaskDuration(Task task) {
		LocalDate startDate = new java.sql.Date(task.getStartDate().getTime()).toLocalDate();
		LocalDate endDate = new java.sql.Date(task.getEndDate().getTime()).toLocalDate();
		return startDate.until(endDate, ChronoUnit.DAYS);
	}
}
