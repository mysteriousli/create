package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import edu.qit.cloudclass.dao.UserMapper;
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

    private final UserMapper userMapper;

    @Override
    public ServerResponse<Void> register(String name, String password, String email) {
        User user = new User();
        user.setId(Tool.uuid());
        user.setName(name);
        user.setPassword(BCrypt.hashpw(password,BCrypt.gensalt()));
        user.setCreateTime(new Date());
        user.setIdentity(User.STUDENT);
        user.setEmail(email);
        try{
            userMapper.register(user);
            log.info("用户" + user.getId() + "注册成功!");
            return ServerResponse.createBySuccessMsg("注册成功");
        } catch (Exception e){
            log.error("注册失败,用户信息:" + user.toString(),e);
        }
        return ServerResponse.createByError("注册失败");
    }

    @Override
    public ServerResponse<User> login(String name, String password) {
        User user = userMapper.login(name);
        if (user != null && BCrypt.checkpw(password,user.getPassword())){
            log.info("用户" + user.getId() + "登录成功!");
            return ServerResponse.createBySuccess("登录成功",user);
        }
        return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getCode(),"用户名或密码错误");
    }

    @Override
    public String registerAutoLogin(User user) {
        String source = user.getId() + user.hashCode() + System.currentTimeMillis();
        String taken = BCrypt.hashpw(source,BCrypt.gensalt());
        try {
            userMapper.registerAutoLogin(user.getId(), taken);
        }catch (Exception e){
            log.error("自动登录记录失败,用户信息:" + user.toString(),e);
            taken = null;
        }
        return taken;
    }

    @Override
    public ServerResponse<User> autoLogin(String taken) {
        User user = userMapper.autoLogin(taken);
        if (user != null){
            log.info("用户" + user.getId() + "自动登录成功!");
            return ServerResponse.createBySuccess("登录成功",user);
        }
        return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getCode(),"登录失败");
    }
}
