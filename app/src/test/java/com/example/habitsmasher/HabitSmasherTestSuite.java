package com.example.habitsmasher;

import com.example.habitsmasher.test.UserTest;
import com.example.habitsmasher.test.DatePickerDialogFragmentTest;
import com.example.habitsmasher.ui.dashboard.HabitListTest;
import com.example.habitsmasher.ui.dashboard.HabitValidatorTest;
import com.example.habitsmasher.ui.history.HabitEventListTest;
import com.example.habitsmasher.ui.history.HabitEventValidatorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({HabitListTest.class,
                     UserTest.class,
                     HabitValidatorTest.class,
                     HabitEventListTest.class,
                     HabitEventValidatorTest.class,
                     DatePickerDialogFragmentTest.class,
                     UserValidatorTest.class,
        EmailValidatorTest.class})
public class HabitSmasherTestSuite {
}
