package com.spring.utils.rwops;

import pojos.Stage;

import java.util.ArrayList;
import java.util.List;

public interface RWOps {
    int COUNT = 1000;
    int BATCH_SIZE = 100;

    void test();

    default List<Stage> getStageTestRecords() {
        List<Stage> stageList = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            stageList.add(new Stage(String.valueOf(i), "stage" + i, "Hi", "123", i));
        }
        return stageList;
    }

    default List<Stage> getStageTestRecords(int COUNT) {
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

    default List<String> getRecordKeys(int COUNT) {
        List<String> keys = new ArrayList<>();
        for(int i = 0; i < COUNT; i++) {
            keys.add(String.valueOf(i));
        }
        return keys;
    }
}
