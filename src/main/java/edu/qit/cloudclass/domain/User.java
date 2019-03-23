package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author nic
 * @version 1.0
 */
@Data
public class User {
    public static final int STUDENT_IDENTITY = 0;
    public static final int TEACHER_IDENTITY = 1;

    public static final String NAME = "name";
    public static final String EMAIL = "email";

    private User(){};

    public static User createUser(String name,String email){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setCreateTime(new Date());
        user.setTaken("");
        return user;
    }

    @JsonIgnore
    private String id;
    private String name;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Date createTime;
    private int identity;
    private String email;
    @JsonIgnore
    private String taken;
}
