package com.example.habitsmasher;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import android.content.Context;

import androidx.fragment.app.Fragment;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class UserAccountHelperTest {

    @Test
    public void buildUserDataMap_expectCorrectMapConstructed() {
        UserAccountHelper userAccountHelper = new UserAccountHelper(mock(Context.class),
                                                                    mock(Fragment.class));

        User testUser = new User("test", "testUser", "test@gmail.com", "123456", new ArrayList<>(), new ArrayList<>());

        HashMap<String, Object> actualUserDataMap = userAccountHelper.buildUserDataMap(testUser);

        assertEquals(testUser.getUsername(), actualUserDataMap.get("username"));
        assertEquals(testUser.getEmail(), actualUserDataMap.get("email"));
        assertEquals(testUser.getPassword(), actualUserDataMap.get("password"));
        assertEquals(testUser.getId(), actualUserDataMap.get("id"));
    }
}