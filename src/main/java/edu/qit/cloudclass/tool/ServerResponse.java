package edu.qit.cloudclass.tool;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * @author nic
 * @version 1.0
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Getter
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T date;

    private ServerResponse(int status){
        this.status = status;
    }

    private ServerResponse(int status,String msg){
        this(status);
        this.msg = msg;
    }

    private ServerResponse(int status,T date){
        this(status);
        this.date = date;
    }

    private ServerResponse(int status,String msg,T date){
        this(status,msg);
        this.date = date;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccess(T date){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),date);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg,T date){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),msg,date);
    }

    public static <T> ServerResponse<T> createBySuccessMsg(String msg){
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<>(ResponseCode.ERROR.getCode());
    }

    public static <T> ServerResponse<T> createByError(String msg){
        return new ServerResponse<>(ResponseCode.ERROR.getCode(),msg);
    }

    public static <T> ServerResponse<T> createByError(int status,String msg){
        return new ServerResponse<>(status,msg);
    }
}
