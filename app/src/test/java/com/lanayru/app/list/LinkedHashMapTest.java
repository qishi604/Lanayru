package com.lanayru.app.list;

import com.lanayru.model.User;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/4/19
 */
public class LinkedHashMapTest {

    @Test
    public void testLinkedHashMap() {
        LinkedHashMap<String, User> map = new LinkedHashMap<>();
        for (int i = 0, N = 10; i < N; i++) {
            User user = new User(i, "xx" + i , 100 + i);
            map.put(user.getId() + "", user);
        }

        User user = new User(2, "se", 18);
        map.put(user.getId() + "", user);
        user = new User(7, "ll", 16);
        map.put(user.getId() + "", user);

        logMap(map);
    }

    private void logMap(Map map) {
        for (Object value : map.values()) {
            System.out.println(value);
        }
    }
}
