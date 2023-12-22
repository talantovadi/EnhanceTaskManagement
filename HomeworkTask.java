package com.example.taskmanagement;

public class HomeworkTask extends Task{
    Task h = new Task();
    public HomeworkTask(){

    }
    public HomeworkTask(String taskName) {
        h.setTaskName(taskName);
    }
    @Override
    public String toString(){
        return "Homework task: "+ h.getTaskName();
    }
}
