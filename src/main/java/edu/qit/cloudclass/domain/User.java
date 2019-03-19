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
    public final static int STUDENT = 0;
    public final static int TEACHER = 1;

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
