package cn.jxh.learning.swagger.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.jxh.learning.swagger.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "共通管理API")
@RestController
public class CommonController extends BaseController {

    @PostMapping("/duke/login")
    @ApiOperation(value = "DUKE認証", notes = "API-DUKE-AUTH")
    public void API_DUKE_AUTH(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        // ログインユーザーID
        String ldapId = bodyJson.getString("ldapId");
        JSONObject returnJson = super.getJsonData("API-DUKE-AUTH", ldapId);
        ResponseUtils.ok(response, returnJson);
    }
}
