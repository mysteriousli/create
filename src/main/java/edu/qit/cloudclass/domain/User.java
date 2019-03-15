package edu.qit.cloudclass.domain;

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

    private String id;
    private String name;
    private String password;
    private Date createTime;
    private int identity;
    private String email;
}
