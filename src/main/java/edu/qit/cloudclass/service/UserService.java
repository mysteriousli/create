package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.User;

/**
 * @author nic
 * @version 1.0
 */
public interface UserService {
    boolean register(String name,String password,String email);
    User login(String name, String password);
}
