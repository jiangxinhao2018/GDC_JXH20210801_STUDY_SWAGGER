package cn.jxh.learning.swagger.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.jxh.learning.swagger.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description サービス管理Controller
 * @author DXC
 *
 */
@Api(tags = "サービス管理API")
@RestController
@RequestMapping("/services")
public class ServicesController extends BaseController {

    @ApiOperation(value = "開閉状況取得", notes = "API-IN-06")
    @GetMapping("mwb-site-status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search-start-date", value = "検索開始日時(YYYYMMDDhhmmss)", required = true, dataType = "String", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "search-end-date", value = "検索終了日時(YYYYMMDDhhmmss)", required = true, dataType = "String", dataTypeClass = String.class, paramType = "query") })
    public void API_IN_06(HttpServletResponse response,
            @RequestParam(value = "search-start-date") String searchStartDate,
            @RequestParam(value = "search-end-date") String searchEndDate) {
        JSONObject returnJson = super.getJsonData("API-IN-06", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "ダッシュボード情報表示取得", notes = "API-IN-07")
    @GetMapping("mwb-dashboard")
    public void API_IN_07(HttpServletResponse response) {
        JSONObject returnJson = super.getJsonData("API-IN-07", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "未開閉検知メールアドレス情報取得", notes = "API-IN-10")
    @ApiImplicitParam(name = "service-site-id", value = "利用地点ID", required = true, dataType = "String", dataTypeClass = String.class, paramType = "path")
    @GetMapping("mwb-site-contact/{service-site-id}")
    public void API_IN_10(HttpServletResponse response, @PathVariable("service-site-id") String serviceSiteId) {
        JSONObject returnJson = super.getJsonData("API-IN-10", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "未開閉検知メールアドレス登録", notes = "API-IN-11")
    @PostMapping("mwb-site-mailaddress")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_11(@RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        // メールアドレス
        String mailAddress = bodyJson.getString("mail-address");
        JSONObject returnJson = super.getJsonData("API-IN-10", null);
        JSONArray addressList = (JSONArray) returnJson.get("mail-address-list");
        addressList.add(mailAddress);
        returnJson.put("mail-address-list", addressList);
        super.writeJsonData("API-IN-10", null, returnJson);
    }

    @ApiOperation(value = "未開閉検知メールアドレス削除", notes = "API-IN-12")
    @DeleteMapping("mwb-site-mailaddress")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void API_IN_12(@RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        // メールアドレス
        String mailAddress = bodyJson.getString("mail-address");
        JSONObject returnJson = super.getJsonData("API-IN-10", null);
        JSONArray addressList = (JSONArray) returnJson.get("mail-address-list");
        addressList.remove(mailAddress);
        returnJson.put("mail-address-list", addressList);
        super.writeJsonData("API-IN-10", null, returnJson);
    }

    @ApiOperation(value = "未開閉検知サービス設定取得", notes = "API-IN-13")
    @GetMapping("mwb-site-services")
    public void API_IN_13(HttpServletResponse response) {
        JSONObject returnJson = super.getJsonData("API-IN-13", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "未開閉検知配信間隔更新", notes = "API-IN-14")
    @PostMapping("mwb-mail-interval")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_14(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        JSONObject returnJson = super.getJsonData("API-IN-14", null);
        returnJson.put("mail-send-interval", bodyJson.get("mail-send-interval"));
        super.writeJsonData("API-IN-14", null, returnJson);
    }

    @ApiOperation(value = "未開閉検知サービス設定更新", notes = "API-IN-15")
    @PostMapping("mwb-site-service")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_15(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        JSONObject returnJson = super.getJsonData("API-IN-14", null);
        JSONArray updSiteList = bodyJson.getJSONArray("site-list");
        JSONArray oldSiteList = returnJson.getJSONArray("site-list");
        JSONArray newSiteList = new JSONArray();
        for (int i = 0; i < oldSiteList.size(); i++) {
            JSONObject oldSiteJson = oldSiteList.getJSONObject(i);
            boolean notFindFlg = true;
            for (int j = 0; j < updSiteList.size(); j++) {
                JSONObject updSiteJson = updSiteList.getJSONObject(j);
                if (!updSiteJson.getString("service-site-id").equals(oldSiteJson.getString("service-site-id"))) {
                    newSiteList.add(updSiteJson.clone());
                    notFindFlg = false;
                    break;
                }
            }
            if (notFindFlg) {
                newSiteList.add(oldSiteJson.clone());
            }
        }
        returnJson.put("site-list", newSiteList);
        super.writeJsonData("API-IN-14", null, returnJson);
    }

}
