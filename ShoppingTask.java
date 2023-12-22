package com.example.taskmanagement;

public class ShoppingTask extends Task{
    Task s = new Task();
    public ShoppingTask(){

    }
    public ShoppingTask(String taskName) {
        s.setTaskName(taskName);
    }
    @Override
    public String toString(){
        return "Shopping task: "+ s.getTaskName();
    }
}
