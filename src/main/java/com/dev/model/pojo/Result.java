package com.dev.model.pojo;

import cn.hutool.json.JSONObject;
import com.dev.model.context.properties.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;
    public static <T> Result<T> success(T data){
        return new Result(200,"返回成功",data);
    }
    public static Result success(){
        return new Result(200,"返回成功",null);
    }
    public static Result error(int code,String msg){
        return new Result(code,msg,null);
    }
    public static Result error(String msg){
        return new Result(500,msg,null);
    }
    public static Result error(ExceptionEnum exception){
        return new Result(exception.getResultCode(), exception.getResultMsg(),null);
    }

    public String toString(){
        return new JSONObject(this).toString();
    }

}
