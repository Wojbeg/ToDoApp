package com.wojbeg.todoapp

import com.wojbeg.todoapp.db.TaskDaoTest
import com.wojbeg.todoapp.ui.CreateActivityTest
import com.wojbeg.todoapp.ui.MainActivityTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    TaskDaoTest::class,
    TaskParcelTest::class,
    MainActivityTest::class,
    CreateActivityTest::class
)
class AndroidTestSuite