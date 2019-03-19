package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import edu.qit.cloudclass.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author nic
 * @version 1.0
 *
 * controller层负责参数接收,检查,转换内容
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController {

    private static final String SESSION_KEY = "correct_user";
    private static final String AUTO_LOGIN_KEY = "auto_login_taken";

    private final UserService userServer;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ServerResponse register(@RequestBody(required = false) Map<String,String> params){
        if (params == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getCode(),"缺少参数");
        }
        //接收json参数
        String name = params.get("name");
        String password = params.get("password");
        String email = params.get("email");
        //检查参数完整性
        if (Tool.checkParamsNotNull(name,password,email)) {
            //调用service方法
            if (userServer.register(name, password, email)) {
                return ServerResponse.createBySuccessMsg("注册成功");
            } else {
                return ServerResponse.createByError();
            }
        }
        return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getCode(),"缺少参数");
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ServerResponse login(@RequestBody(required = false) Map<String,String> params, HttpServletResponse response, HttpSession session){
        if (params == null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getCode(),ResponseCode.MISSING_ARGUMENT.getMsg());
        }
        //接收json参数
        String name = params.get("name");
        String password = params.get("password");
        log.info(params.get("autoLogin"));
        boolean autoLogin = Boolean.parseBoolean(params.get("autoLogin"));
        //参数完整性检查
        if (Tool.checkParamsNotNull(name,password)){
            //调用Service方法验证登录
            ServerResponse<User> result = userServer.login(name,password);
            if (result.isSuccess()){
                //记录会话信息
                session.setAttribute(SESSION_KEY,result.getDate());
                log.info(String.valueOf(autoLogin));
                if (autoLogin){
                    log.info("flag");
                    //自动登录
                    String taken = userServer.registerAutoLogin(result.getDate());
                    Cookie cookie = new Cookie(AUTO_LOGIN_KEY,taken);
                    cookie.setMaxAge(60*60*24*30);
                    response.addCookie(cookie);
                }
            }
            return result;
        }
        return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getCode(),"缺少参数");
    }

    @RequestMapping(value = "/login/auto",method = RequestMethod.POST)
    public ServerResponse autoLogin(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if (cookie.getName().equals(AUTO_LOGIN_KEY)){
                return userServer.autoLogin(cookie.getValue());
            }
        }
        return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getCode(),"未找到凭证");
    }
}
