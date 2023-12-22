package com.example.taskmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;


public class HelloController {
    @FXML
    private ListView<Task> listView;
    ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> taskChoiceBox;
    protected String[] tasksType = {"Homework task","Meeting task", "Shopping task"};
    @FXML
    private TextField nameTask;
    @FXML
    private TextField descriptionTask;
    @FXML
    private DatePicker deadLine;
    @FXML
    private ToggleGroup radioGroup;
    @FXML
    private RadioButton lowRadioButton;
    @FXML
    private RadioButton mediumRadioButton;
    @FXML
    private RadioButton highRadioButton;
    @FXML
    private CheckBox checkBox;

    HomeworkTask ht;
    MeetingTask mt;
    ShoppingTask st;
    TaskDAO db = new TaskDAO();

    public void initialize() {
        listView.setItems(tasks);
        taskChoiceBox.getItems().addAll(tasksType);
        db.read(tasks);
    }
    @FXML
    protected void onSaveButtonClick() {
        String type = taskChoiceBox.getValue();
        Date userDeadline = null;
        if (type != null && nameTask != null) {
            if (deadLine.getValue() != null) {
                userDeadline = Date.from(deadLine.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            }

            Task existingTask = null;
            for (Task t : tasks) {
                if (t.getTaskName().equals(nameTask.getText())) {
                    existingTask = t;
                    break;
                }
            }
            if (existingTask != null) {
                existingTask.setTask(nameTask.getText(), descriptionTask.getText());
                existingTask.setDeadline(userDeadline);
                RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
                String selectedRadioButtonText = selectedRadioButton.getText();
                Priority priority = Priority.valueOf(selectedRadioButtonText);
                existingTask.setPriority(priority);
                existingTask.markAsComplete(checkBox.isSelected());
                java.sql.Date sqlDeadline = null;
                if (deadLine.getValue() != null) {
                    sqlDeadline = java.sql.Date.valueOf(deadLine.getValue());
                }
//                java.sql.Date sqlDeadline = java.sql.Date.valueOf(deadLine.getValue());
                db.update(type, nameTask.getText(), descriptionTask.getText(), sqlDeadline, priority, checkBox.isSelected());
            } else {
                if (type.equals("Homework task")) {
                    ht = new HomeworkTask(nameTask.getText());
                    ht.setTask(nameTask.getText(), descriptionTask.getText());
                    tasks.add(ht);
                    ht.setDeadline(userDeadline);
                    RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
                    String selectedRadioButtonText = selectedRadioButton.getText();
                    Priority priority = Priority.valueOf(selectedRadioButtonText);
                    ht.setPriority(priority);
                    ht.markAsComplete(checkBox.isSelected());
                } else if (type.equals("Meeting task")) {
                    mt = new MeetingTask(nameTask.getText());
                    mt.setTask(nameTask.getText(), descriptionTask.getText());
                    tasks.add(mt);
                    mt.setDeadline(userDeadline);
                    RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
                    String selectedRadioButtonText = selectedRadioButton.getText();
                    Priority priority = Priority.valueOf(selectedRadioButtonText);
                    mt.setPriority(priority);
                    mt.markAsComplete(checkBox.isSelected());
                } else if (type.equals("Shopping task")) {
                    st = new ShoppingTask(nameTask.getText());
                    st.setTask(nameTask.getText(), descriptionTask.getText());
                    tasks.add(st);
                    st.setDeadline(userDeadline);
                    RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
                    String selectedRadioButtonText = selectedRadioButton.getText();
                    Priority priority = Priority.valueOf(selectedRadioButtonText);
                    st.setPriority(priority);
                    st.markAsComplete(checkBox.isSelected());
                }
                RadioButton selectedRadioButton = (RadioButton) radioGroup.getSelectedToggle();
                String selectedRadioButtonText = selectedRadioButton.getText();
                Priority priority = Priority.valueOf(selectedRadioButtonText);
                java.sql.Date sqlDeadline = null;
                if (deadLine.getValue() != null) {
                    sqlDeadline = java.sql.Date.valueOf(deadLine.getValue());
                }
                db.create(type, nameTask.getText(), descriptionTask.getText(), sqlDeadline, priority, checkBox.isSelected());
            }
        }
        taskChoiceBox.setValue("");
        nameTask.setText("");
        descriptionTask.setText("");
        deadLine.setValue(null);
        lowRadioButton.setSelected(true);
        checkBox.setSelected(false);
    }

    @FXML
    protected void onListViewSelected(){
        Task selectedTask = listView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            if (selectedTask instanceof HomeworkTask ht) {
                taskChoiceBox.setValue("Homework task");
                nameTask.setText(ht.getTaskName());
                descriptionTask.setText(ht.getTaskDescription());
                if (ht.getDeadline() != null) {
                    java.util.Date utilDate = new java.util.Date(ht.getDeadline().getTime());
                    deadLine.setValue(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                lowRadioButton.setSelected(ht.getPriority() == Priority.LOW);
                mediumRadioButton.setSelected(ht.getPriority() == Priority.MEDIUM);
                highRadioButton.setSelected(ht.getPriority() == Priority.HIGH);
                checkBox.setSelected(ht.isCompleted());
            }
            else if (selectedTask instanceof MeetingTask mt) {
                taskChoiceBox.setValue("Meeting task");
                nameTask.setText(mt.getTaskName());
                descriptionTask.setText(mt.getTaskDescription());
                if (mt.getDeadline() != null) {
                    java.util.Date utilDate = new java.util.Date(mt.getDeadline().getTime());
                    deadLine.setValue(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                lowRadioButton.setSelected(mt.getPriority() == Priority.LOW);
                mediumRadioButton.setSelected(mt.getPriority() == Priority.MEDIUM);
                highRadioButton.setSelected(mt.getPriority() == Priority.HIGH);
                checkBox.setSelected(mt.isCompleted());
            }
            else if (selectedTask instanceof ShoppingTask st) {
                taskChoiceBox.setValue("Shopping task");
                nameTask.setText(st.getTaskName());
                descriptionTask.setText(st.getTaskDescription());
                if (st.getDeadline() != null) {
                    java.util.Date utilDate = new java.util.Date(st.getDeadline().getTime());
                    deadLine.setValue(utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                lowRadioButton.setSelected(st.getPriority() == Priority.LOW);
                mediumRadioButton.setSelected(st.getPriority() == Priority.MEDIUM);
                highRadioButton.setSelected(st.getPriority() == Priority.HIGH);
                checkBox.setSelected(st.isCompleted());
            }
        }
    }

    @FXML
    public void onClearButtonClick() {
        taskChoiceBox.setValue("");
        nameTask.clear();
        descriptionTask.clear();
        deadLine.setValue(null);
        lowRadioButton.setSelected(true);
        checkBox.setSelected(false);
    }

    @FXML
    public void onDeleteButtonClick() {
        Task selectedTask = listView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasks.remove(selectedTask);
        }
        String task = String.valueOf(selectedTask);
        String type = task.substring(0, task.indexOf(":"));
        String name = task.substring(task.indexOf(":")+2);
        db.delete(type, name);
        onClearButtonClick();
    }
}