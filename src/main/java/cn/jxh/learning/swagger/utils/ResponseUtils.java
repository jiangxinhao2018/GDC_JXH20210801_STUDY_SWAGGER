package cn.jxh.learning.swagger.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;

public class ResponseUtils {

    public static void error(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public static void error(HttpServletResponse response, Object obj) {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        try {
            response.getWriter().write(JSON.toJSONString(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ok(HttpServletResponse response, Object obj) {

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        try {
            response.getWriter().write(JSON.toJSONString(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ok(HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
    }

    public static void customHttpStatus(HttpServletResponse response, int status, Object obj) {

        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        try {
            response.getWriter().write(JSON.toJSONString(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
