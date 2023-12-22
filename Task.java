package com.example.taskmanagement;

import java.util.Date;

public class Task implements TaskInterface{
    private  int taskId;
    private String taskType;
    private String taskName;
    private String taskDescription;
    private boolean completed;
    private Priority priority;
    private Date deadline;

    public int getTaskId() {
        return taskId;
    }
    public String getTaskType(){
        return taskType;
    };

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    @Override
    public void setTask(String taskName, String taskDescription){
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.completed = false;
    }
    @Override
    public void setTaskType(String taskType){
        this.taskType = taskType;
    };
    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    @Override
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    @Override
    public void markAsComplete(boolean completed) {
        this.completed = completed;
    }
    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    @Override
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
