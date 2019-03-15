package edu.qit.cloudclass.controller;

import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.tool.JsonState;
import edu.qit.cloudclass.tool.Tool;
import edu.qit.cloudclass.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class UserController {

    private final UserService service;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map<String,Object> register(@RequestBody(required = false) Map<String,String> params){
        Map<String,Object> result;
        //接收json参数
        String name = params.get("name");
        String password = params.get("password");
        String email = params.get("email");
        //检查参数完整性
        if (Tool.checkParamsNotNull(name,password,email)) {
            //如果有类型转换需要在此检查
            //调用service方法
            if (service.register(name, password, email)) {
                result = Tool.genResultMap(JsonState.SUCCESS,null);
            } else {
                result = Tool.genResultMap(JsonState.UNKNOWN_ERROR,null);
            }
        }
        else {
            result = Tool.genResultMap(JsonState.MISSING_REQUIRED_PARAM,null);
        }
        return result;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody Map<String,String> params, HttpSession session){
        Map<String,Object> result;
        String name = params.get("name");
        String password = params.get("password");
        if (Tool.checkParamsNotNull(name,password)){
            User user = service.login(name,password);
            if (user != null){
                session.setAttribute("user_id",user.getId());
                result = Tool.genResultMap(JsonState.SUCCESS,null);
            }
            else {
                result = Tool.genResultMap(JsonState.PERMISSION_DENIED,null);
            }
        }
        else {
            result = Tool.genResultMap(JsonState.MISSING_REQUIRED_PARAM,null);
        }
        return result;
    }
}
