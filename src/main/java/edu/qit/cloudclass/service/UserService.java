package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 */
public interface UserService {
    boolean register(String name,String password,String email);
    ServerResponse<User> login(String name, String password);
    String registerAutoLogin(User user);
    ServerResponse<User> autoLogin(String taken);
}
