package com.luan.getlib.models;

/**
 * @since v1.3.0
 * @author luanpozzobon
 */
public class Result<T> {
    public boolean result;
    public String message;
    public T entity;

    public Result(boolean result, String message, T entity){
        this.result = result;
        this.message = message;
        this.entity = entity;
    }
}
