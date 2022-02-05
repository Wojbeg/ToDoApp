package com.wojbeg.todoapp.utils

import com.wojbeg.todoapp.model.Task
import java.time.OffsetDateTime

object TasksConstants {

    val date: OffsetDateTime = OffsetDateTime.now()

    val NUMBER_OF_TASKS_FOR_TEST = 12

    val task1 = Task(1, false, "a", "Work", date.plusDays(10), Priority.Normal)
    val task2 = Task(2, false, "b", "Shopping", date, Priority.Medium)
    val task3 = Task(3, true, "c", "Education", date.minusDays(20), Priority.High)
    val task4 = Task(4, false, "d", "Sports", date, Priority.Extreme)
    val task5 = Task(5, true, "e", "Entertainment", date.plusHours(5), Priority.Normal)
    val task6 = Task(6, false, "f", "Other", date, Priority.Low)
    val task7 = Task(7, false, "g", "Education", date.plusYears(1), Priority.Low)
    val task8 = Task(8, true, "h", "Work", date, Priority.Medium)
    val task9 = Task(9, false, "i", "Education", date.plusHours(15), Priority.Normal)
    val task10 = Task(10, false, "j", "Work", date, Priority.Low)
    val task11 = Task(11, true, "k", "Education", date, Priority.Extreme)
    val task12 = Task(12, false, "l", "Education", date, Priority.High)

    val TEST_TASK_TO_ADD_AND_DELETE = Task(15, false, "a", "Work", date, Priority.Normal)
    val TEST_TASK_EMPTY_TITLE_ERROR = Task(14, true, " ", "Shopping", date, Priority.Low)

    val tasks = mutableListOf(task1, task2, task3, task4, task5, task6, task7, task8, task9, task10, task11, task12)

    val sortedById = listOf(task1, task2, task3, task4, task5, task6, task7, task8, task9, task10, task11, task12)
    val sortedByStatus = listOf(task1, task2, task4, task6, task7, task9, task10, task12, task3, task5, task8, task11)
    val sortedByName = listOf(task1, task2, task3, task4, task5, task6, task7, task8, task9, task10, task11, task12)
    val sortedByDate = listOf(task3, task2, task4, task6, task8, task10, task11, task12, task5, task9, task1, task7)
    val sortedByPriority = listOf(task4, task11, task3, task12, task2, task8, task1, task5, task9, task6, task7, task10)
    val sortedByType = listOf(task3, task7, task9, task11, task12, task5, task6, task2, task4, task1, task8, task10)


}