package com.example.taskmanagement;

public class MeetingTask extends Task{
    Task m = new Task();
    public MeetingTask(){

    }
    public MeetingTask(String taskName) {
        m.setTaskName(taskName);
    }
    @Override
    public String toString(){
        return "Meeting task: "+ m.getTaskName();
    }
}
