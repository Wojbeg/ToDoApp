package com.wojbeg.todoapp.utils;

import com.wojbeg.todoapp.model.Task;

import java.util.Comparator;

public class Comparators {

    public static Comparator<Task> TaskNameAZComparator = new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            return task1.getName().compareToIgnoreCase(task2.getName());
        }
    };

    public static Comparator<Task> TaskDateAsc = new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            return (int) (task1.getDate().toZonedDateTime().compareTo(task2.getDate().toZonedDateTime()));
        }
    };

    public static Comparator<Task> TaskDateDsc = new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            return (int) (task2.getDate().toZonedDateTime().compareTo(task1.getDate().toZonedDateTime()));
        }
    };

    public static Comparator<Task> TaskTypeAsc = new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            return task1.getType().compareToIgnoreCase(task2.getType());
        }
    };

    public static Comparator<Task> TaskStatus = new Comparator<Task>() {
        @Override
        public int compare(Task task1, Task task2) {
            return Boolean.compare(task1.getStatus(), task2.getStatus());
        }
    };

}
