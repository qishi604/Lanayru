package com.lanayru.app.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class DynamicProxyTest {

    interface UserApi {

        String login(String account, String password);
    }

    @SuppressWarnings("unchecked")
    private <T> T create(Class<T> cls) {
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                String name = method.getName();

                System.out.println("invoke method " + name);

                return name;
            }
        });
    }

    @Test
    public void testProxy() {
        UserApi api = create(UserApi.class);
        String result = api.login("user", "admin");

        System.out.println("result " + result);
    }
}
