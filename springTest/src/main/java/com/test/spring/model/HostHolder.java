package com.test.spring.model;

import org.springframework.stereotype.Component;

/**
 * @author xiang
 */
@Component
public class HostHolder {
    //保证每个访问服务器的客户只使用自己的user
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();;
    }
}
