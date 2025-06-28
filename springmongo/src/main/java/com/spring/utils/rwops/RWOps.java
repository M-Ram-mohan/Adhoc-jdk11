package com.spring.utils.rwops;

import pojos.Stage;

import java.util.ArrayList;
import java.util.List;

public interface RWOps {
    static int COUNT = 1000;

    static int BATCH_SIZE = 100;

    public void test();

    default List<Stage> getStageTestRecords() {
        List<Stage> stageList = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            stageList.add(new Stage(String.valueOf(i), "stage" + i, "Hi", "123", i));
        }
        return stageList;
    }

    default List<String> getRecordKeys() {
        List<String> keys = new ArrayList<>();
        for(int i = 0; i < COUNT; i++) {
            keys.add(String.valueOf(i));
        }
        return keys;
    }
}
