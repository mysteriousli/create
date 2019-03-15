package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.tool.Tool;
import edu.qit.cloudclass.dao.UserDao;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author nic
 * @version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao dao;

    @Override
    public boolean register(String name, String password, String email) {
        boolean result = false;
        User user = new User();
        user.setId(Tool.uuid());
        user.setName(name);
        user.setPassword(BCrypt.hashpw(password,BCrypt.gensalt()));
        user.setCreateTime(new Date());
        user.setIdentity(User.STUDENT);
        user.setEmail(email);
        try{
            dao.register(user);
            result = true;
            log.info("用户" + user.getId() + "注册成功!");
        } catch (Exception e){
            log.error("注册失败,用户信息:" + user.toString(),e);
        }
        return result;
    }

    @Override
    public User login(String name, String password) {
        User user = dao.login(name);
        if (user != null){
            if (BCrypt.checkpw(password,user.getPassword())){
                log.info("用户" + user.getId() + "登录成功!");
            }
            else {
                user = null;
            }
        }
        return user;
    }

    @Override
    public User getInfo(String id) {
        return dao.getInfo(id);
    }
}
