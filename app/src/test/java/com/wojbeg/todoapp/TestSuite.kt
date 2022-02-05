package com.wojbeg.todoapp

import com.wojbeg.todoapp.repository.FakeRepositoryTest
import com.wojbeg.todoapp.repository.RepositoryMockTest
import com.wojbeg.todoapp.viewModel.Create.CreateTaskViewModelTest
import com.wojbeg.todoapp.viewModel.Main.MainViewModelMockTest
import com.wojbeg.todoapp.viewModel.Main.MainViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    FakeRepositoryTest::class,
    RepositoryMockTest::class,
    MainViewModelTest::class,
    MainViewModelMockTest::class,
    CreateTaskViewModelTest::class
)
class TestSuite