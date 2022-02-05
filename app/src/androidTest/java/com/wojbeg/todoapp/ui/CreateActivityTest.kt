package com.wojbeg.todoapp.ui

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.wojbeg.todoapp.utils.Priority
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.widget.DatePicker

import androidx.test.espresso.matcher.ViewMatchers.withClassName
import com.wojbeg.todoapp.R
import org.hamcrest.Matchers


@RunWith(AndroidJUnit4::class)
@LargeTest
class CreateActivityTest {

    private lateinit var nameToBeTyped: String

    @get:Rule
    var activityRule: ActivityScenarioRule<CreateTask>
     = ActivityScenarioRule(CreateTask::class.java)

    @Before
    fun initValidStrings(){
        nameToBeTyped = "Espresso"
    }

    @Test
    fun noParcelPassed_titleIsEqualToCreateNewTask(){
        onView(withId(R.id.newTaskTitle))
            .check(matches(withText(R.string.createNewTask)))
    }

    @Test
    fun changeTaskName_sameActivity(){
        onView(withId(R.id.taskName))
            .perform(typeText(nameToBeTyped), closeSoftKeyboard())

        onView(withId(R.id.taskName))
            .check(matches(withText(nameToBeTyped)))
    }

    @Test
    fun taskTypeSpinnerSelection_ShoppingSet(){
        onView(withId(R.id.typeSpinner)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.typeSpinner)).check(matches(withSpinnerText(containsString("Shopping"))))
    }

    @Test
    fun prioritySpinnerSelection_HighSet(){
        onView(withId(R.id.prioritySpinner)).perform(click())
        onData(anything()).atPosition(1).perform(click())
        onView(withId(R.id.prioritySpinner)).check(matches(withSpinnerText(containsString(Priority.High.name))))
    }

    @Test
    fun isDatePickerDialogShowing_true(){
        onView(withId(R.id.changeDateBtn)).perform(click())
        onView(withId(android.R.id.button1)).check(matches(isDisplayed()));
    }

    @Test
    fun changeDateSelection_dateValuesChanges(){
        onView(withId(R.id.changeDateBtn)).perform(click())

        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(2021, 12, 12))

        onView(withId(android.R.id.button1)).perform(click())

        onView(withId(R.id.constraintLayout)).check(matches(allOf(
            hasDescendant(allOf(withId(R.id.dayValue), withText("12"))),
            hasDescendant(allOf(withId(R.id.monthValue), withText("12"))),
            hasDescendant(allOf(withId(R.id.yearValue), withText("2021"))),
        )))
    }

    @Test
    fun emptyName_createsError(){
        onView(withId(R.id.saveBtn)).perform(click())
        onView(withId(R.id.taskName)).check(matches(hasErrorText("Task name cannot be empty")))
    }

}