package cn.jxh.learning.swagger.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.jxh.learning.swagger.entity.User;
import cn.jxh.learning.swagger.utils.CommonUtils;
import cn.jxh.learning.swagger.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Description ユーザー管理Controller
 * @Author DXC
 **/
@Slf4j
@Api(tags = "ユーザー管理API")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @ApiIgnore
    @ApiOperation(value = "get all users", notes = "获取用户列表")
    @GetMapping("list")
    public List<User> getUserList() {
        return Arrays.asList(User.builder().age(18).username("枣面包").build());
    }

    @ApiOperation(value = "管理Web用端末登録", notes = "API-IN-01")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("mwb-login")
    public void API_IN_01(HttpServletResponse response) {
        Object returnJson = super.getJsonData("API-IN-01", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "管理Web管理者用トークン取得", notes = "API-IN-02")
    @PostMapping("mwb-admin-token")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_02(HttpServletResponse response) {
        JSONObject returnJson = new JSONObject();
        returnJson.put("login-token", CommonUtils.generateToken());
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "オーナー/管理者情報取得", notes = "API-IN-03")
    @PostMapping("mwb-owners")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_03(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        // 検索条件文字列
        String contractorSearch = bodyJson.getString("contractor-search");
        Object returnJson = super.getJsonData("API-IN-03", contractorSearch);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "管理Web管理者用セッション取得・更新", notes = "API-IN-04")
    @PutMapping("mwb-admin-session")
    public void API_IN_04(HttpServletResponse response) {
        Object returnJson = super.getJsonData("API-IN-04", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "物件リスト取得", notes = "API-IN-05")
    @GetMapping("mwb-properties")
    public void API_IN_05(HttpServletResponse response) {
        Object returnJson = super.getJsonData("API-IN-05", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "選択済みサービス利用番号更新", notes = "API-IN-08")
    @PutMapping("mwb-contractor")
    public void API_IN_08(@RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        // 検索条件文字列
        String contractorId = bodyJson.getString("contractor-id");
        log.debug("選択済みサービス利用番号「" + contractorId + " 」更新成功。");
    }

    @ApiOperation(value = "部屋番号リスト取得", notes = "API-IN-09")
    @GetMapping("mwb-sites")
    public void API_IN_09(HttpServletResponse response) {
        Object returnJson = super.getJsonData("API-IN-09", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "管理Web用ログアウト", notes = "API-IN-23")
    @PostMapping("mwb-logout")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_23(HttpServletResponse response) {
        Object returnJson = super.getJsonData("API-IN-23", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "管理Web用利用者認証", notes = "API-IN-24")
    @PostMapping("mwb-authentication")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_24(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        // 検索条件文字列
        String loginId = bodyJson.getString("login-id");
        Object returnJson = super.getJsonData("API-IN-24", loginId);
        ResponseUtils.ok(response, returnJson);
    }
}
