package com.wojbeg.todoapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.wojbeg.todoapp.R
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.utils.Priority
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.OffsetDateTime


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
     = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_navToCreateTaskActivity(){
        onView(withId(R.id.addTaskBtn)).perform(click())

        onView(withId(R.id.newTaskTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navBackToMainActivity_byBackBtn(){
        onView(withId(R.id.addTaskBtn)).perform(click())

        onView(withId(R.id.newTaskTitle)).check(matches(isDisplayed()))
        pressBack()

        onView(withId(R.id.AppTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navBackToMainActivity_byCloseBtn(){
        onView(withId(R.id.addTaskBtn)).perform(click())

        onView(withId(R.id.newTaskTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.cancelBtn)).perform(click())

        onView(withId(R.id.AppTitle)).check(matches(isDisplayed()))
    }

    @Test
    fun test_NoTasks_AddTaskMessageDisplayed(){
        onView(withId(R.id.noTasksImage)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.NoTasksInfo)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.NoTasksMoreInfo)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }



}