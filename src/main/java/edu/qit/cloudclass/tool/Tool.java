package edu.qit.cloudclass.tool;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author nic
 * @version 1.0
 */
public class Tool {
    /**
     * 获取随机UUID
     * @return 随机UUID
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }

    /**
     * 检查参数是否有效
     * @param params 待检查参数
     * @return 检查结果
     */
    public static boolean checkParamsNotNull(String... params){
        for (String param: params) {
            if (param == null || param.length() == 0){
                return false;
            }
        }
        return true;
    }

    /**
     * 打包返回的数据
     * @param state 相应状态
     * @param date date数据
     * @return 响应数据
     */
    public static Map<String,Object> genResultMap(JsonState state,Object date){
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("code",state.getCode());
        result.put("msg",state.getMsg());
        result.put("date",date);
        return result;
    }
}
