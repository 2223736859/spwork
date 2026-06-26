package com.example.spwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
  private int code;
  private String msg;
  private T data;

  public static <T> Response<T> success(T data){
    return new Response<>(200,"success",data);
  }

  public static <T> Response<T> failure(int code,String msg){
    return new Response<>(code,msg,null);
  }

}