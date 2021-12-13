package cn.jxh.learning.swagger.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "search-start-date", value = "検索開始日時(YYYYMMDDhhmmss)", required = true, dataType = "String", dataTypeClass = String.class, paramType = "query"),
        @ApiImplicitParam(name = "search-end-date", value = "検索終了日時(YYYYMMDDhhmmss)", required = true, dataType = "String", dataTypeClass = String.class, paramType = "query")
    })
    public void API_IN_06(HttpServletResponse response,
            @RequestParam(value = "search-start-date") String searchStartDate,
            @RequestParam(value = "search-end-date") String searchEndDate) {
        JSONObject returnJson = super.getJsonData("API-IN-06", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "ダッシュボード情報表示取得", notes = "API-IN-07")
    @GetMapping("mwb-dashboard")
    @ResponseStatus(code = HttpStatus.OK)
    public void API_IN_07(HttpServletResponse response) {
        JSONObject returnJson = super.getJsonData("API-IN-07", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "未開閉検知メールアドレス情報取得", notes = "API-IN-10")
    @GetMapping("mwb-site-contact/{service-site-id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParam(name = "service-site-id", value = "利用地点ID", required = true, dataType = "String", dataTypeClass = String.class, paramType = "path")
    public void API_IN_10(HttpServletResponse response, @PathVariable("service-site-id") String serviceSiteId) {
        JSONObject returnJson = super.getJsonData("API-IN-10", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "未開閉検知メールアドレス登録", notes = "API-IN-11")
    @PostMapping("mwb-site-mailaddress")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_11(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        // メールアドレス
        String mailAddress = bodyJson.getString("mail-address");
        JSONObject returnJson = super.getJsonData("API-IN-10", null);
        JSONArray addressList = (JSONArray) returnJson.get("mail-address-list");
        addressList.add(mailAddress);
        returnJson.put("mail-address-list", addressList);
        super.writeJsonData("API-IN-10", null, returnJson);
        ResponseUtils.ok(response);
    }

    @ApiOperation(value = "未開閉検知メールアドレス削除", notes = "API-IN-12")
    @DeleteMapping("mwb-site-mailaddress")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void API_IN_12(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        // メールアドレス
        String mailAddress = bodyJson.getString("mail-address");
        JSONObject returnJson = super.getJsonData("API-IN-10", null);
        JSONArray addressList = (JSONArray) returnJson.get("mail-address-list");
        addressList.remove(mailAddress);
        returnJson.put("mail-address-list", addressList);
        super.writeJsonData("API-IN-10", null, returnJson);
        ResponseUtils.ok(response);
    }

    @ApiOperation(value = "未開閉検知サービス設定取得", notes = "API-IN-13")
    @GetMapping("mwb-site-services")
    @ResponseStatus(code = HttpStatus.OK)
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
        ResponseUtils.ok(response);
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
        ResponseUtils.ok(response);
    }

    @ApiOperation(value = "架電結果登録", notes = "API-IN-25")
    @PostMapping("mwb-elec-status")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_25(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        String contractorId = bodyJson.getString("contractor-id");
        JSONObject returnJson = new JSONObject();
        JSONObject jsonObject;
        // 開閉状況取得の場合
        if (contractorId.compareTo("CON00010000") > 0) {
            jsonObject = super.getJsonData("API-IN-06", null);
        } else {
            // ダッシュボード表示情報取得の場合
            jsonObject = super.getJsonData("API-IN-07", null);
        }
        JSONArray notOpencloseList = jsonObject.getJSONArray("not-openclose-list");
        for (Object item : notOpencloseList) {
            JSONObject notOpenclose = (JSONObject) item;
            if (bodyJson.getString("service-site-id").equals(notOpenclose.getString("service-site-id")) && bodyJson.getString("event-date-seq").equals(notOpenclose.getString("event-date-seq"))) {
                notOpencloseList.remove(notOpenclose);
                notOpenclose.put("elec-status-sp1", bodyJson.getString("elec-status-sp1"));
                notOpenclose.put("elec-status-sp2", bodyJson.getString("elec-status-sp2"));
                notOpenclose.put("elec-status-con1", bodyJson.getString("elec-status-con1"));
                notOpenclose.put("elec-status-con2", bodyJson.getString("elec-status-con2"));
                notOpenclose.put("mail-status", bodyJson.getString("mail-status"));
                notOpenclose.put("elec-comment", bodyJson.getString("elec-comment"));
                notOpenclose.put("send-mail-flg", bodyJson.getString("send-mail-flg"));
                if ("1".equals(bodyJson.getString("send-mail-flg"))) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    returnJson.put("email-result-date", df.format(new Date()));
                    returnJson.put("email-result-status", "0");
                }
                notOpencloseList.add(notOpenclose);
                break;
            }
        }
        jsonObject.put("not-openclose-list", notOpencloseList);
        // 開閉状況取得の場合
        if (contractorId.compareTo("CON00010000") > 0) {
            super.writeJsonData("API-IN-06", null, jsonObject);
        } else {
            // ダッシュボード表示情報取得の場合
            super.writeJsonData("API-IN-07", null, jsonObject);
        }
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "未開閉検知電話番号登録", notes = "API-IN-26")
    @PostMapping("mwb-site-phonenumber")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_26(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        JSONObject jsonObject= super.getJsonData("API-IN-10", null);
        if (StringUtils.isNotBlank(bodyJson.getString("phone-number"))) {
            jsonObject.put("phone-number", bodyJson.getString("phone-number"));
        }
        if (StringUtils.isNotBlank(bodyJson.getString("emergency-phone-number"))) {
            jsonObject.put("emergency-phone-number", bodyJson.getString("emergency-phone-number"));
        }
        super.writeJsonData("API-IN-10", null, jsonObject);
        ResponseUtils.ok(response);
    }

    @ApiOperation(value = "物件連絡先情報取得", notes = "API-IN-27")
    @GetMapping("mwb-properties-contact/{contractor-id}")
    @ResponseStatus(code = HttpStatus.OK)
    @ApiImplicitParam(name = "contractor-id", value = "サービス利用番号", required = true, dataType = "String", dataTypeClass = String.class, paramType = "path")
    public void API_IN_27(HttpServletResponse response, @PathVariable("contractor-id") String contractorId) {
        JSONObject returnJson = super.getJsonData("API-IN-27", null);
        JSONObject contractorObject = returnJson.getJSONObject(contractorId);
        ResponseUtils.ok(response, contractorObject);
    }

    @ApiOperation(value = "物件連絡先情報登録", notes = "API-IN-28")
    @PostMapping("mwb-contractor-contact")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_28(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject jsonObject= super.getJsonData("API-IN-27", null);
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        // サービス利用番号
        String contractorId = bodyJson.getString("contractor-id");
        JSONObject contractorObject = jsonObject.getJSONObject(contractorId);
        if (contractorObject == null || contractorObject.isEmpty()) {
            contractorObject = new JSONObject();
        }
        JSONArray addressList = (JSONArray) bodyJson.get("mgmnt-mail-address-list");
        if (addressList != null && addressList.size() > 0) {
            contractorObject.put("mgmnt-mail-address-list", addressList);
        }
        JSONArray phoneList = (JSONArray) bodyJson.get("mgmnt-phone-number-list");
        if (phoneList != null && phoneList.size() > 0) {
            contractorObject.put("mgmnt-phone-number-list", phoneList);
        }
        jsonObject.put(contractorId, contractorObject);
        super.writeJsonData("API-IN-27", null, jsonObject);
        ResponseUtils.ok(response);
    }

    @ApiOperation(value = "物件連絡先情報削除", notes = "API-IN-29")
    @DeleteMapping("mwb-contractor-contact")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void API_IN_29(HttpServletResponse response, @RequestBody String jsonStr) {
        JSONObject bodyJson = JSON.parseObject(jsonStr);
        // サービス利用番号
        String contractorId = bodyJson.getString("contractor-id");
        JSONObject apiJson = super.getJsonData("API-IN-27", null);
        JSONObject contractorObject = apiJson.getJSONObject(contractorId);
        if (!(contractorObject == null || contractorObject.isEmpty())) {
            // メールアドレス
            String mailAddress = bodyJson.getString("mail-address");
            JSONArray addressList = (JSONArray) contractorObject.get("mgmnt-mail-address-list");
            addressList.remove(mailAddress);
            contractorObject.put("mgmnt-mail-address-list", addressList);
            apiJson.put(contractorId, contractorObject);
            super.writeJsonData("API-IN-27", null, apiJson);
        }
        ResponseUtils.ok(response);
    }
}

