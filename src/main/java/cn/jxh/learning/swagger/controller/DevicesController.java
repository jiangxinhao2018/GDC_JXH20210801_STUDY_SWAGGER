package cn.jxh.learning.swagger.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.jxh.learning.swagger.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description デバイス管理Controller
 * @author DXC
 *
 */
@Api(tags = "デバイス管理API")
@RestController
@RequestMapping("/devices")
public class DevicesController extends BaseController {

    @ApiOperation(value = "機器状態取得", notes = "API-IN-16")
    @GetMapping("mwb-devices")
    public void API_IN_16(HttpServletResponse response) {
        Object returnJson = super.getJsonData("API-IN-16", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "管理Web向け全HGW更新要求", notes = "API-IN-17")
    @PostMapping("mwb-batch-firmware")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void API_IN_17(HttpServletResponse response) {
        Object returnJson = super.getJsonData("API-IN-17", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "管理Web向けHGW更新要求", notes = "API-IN-18")
    @PutMapping("mwb-firmware")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void API_IN_18(HttpServletResponse response) {
        Object returnJson = super.getJsonData("API-IN-18", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "管理者向け機器状態取得", notes = "API-IN-19")
    @GetMapping("mwb-admin-devices")
    public void API_IN_19(HttpServletResponse response) {
        Object returnJson = super.getJsonData("API-IN-19", null);
        ResponseUtils.ok(response, returnJson);
    }
    
    @ApiOperation(value = "管理Web向けHGW登録", notes = "API-IN-20")
    @PostMapping("mwb-hgw-serialno")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void API_IN_20(HttpServletResponse response, @RequestBody String jsonStr) {
        Object returnJson = super.getJsonData("API-IN-20", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "管理Web向けHGWシリアル番号更新", notes = "API-IN-21")
    @PutMapping("mwb-hgw-serialno")
    public void API_IN_21(HttpServletResponse response) {
        Object returnJson = super.getJsonData("API-IN-21", null);
        ResponseUtils.ok(response, returnJson);
    }

    @ApiOperation(value = "管理Web向けデバイス情報削除", notes = "API-IN-22")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "device-id", value = "利用機器ID", required = true, dataType = "String", dataTypeClass = String.class, paramType = "path"),
        @ApiImplicitParam(name = "service-site-id", value = "利用地点ID", required = true, dataType = "String", dataTypeClass = String.class, paramType = "query") })
    @DeleteMapping("mwb-device/{device-id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void API_IN_22(HttpServletResponse response, @PathVariable("device-id")String deviceId, @RequestParam(value = "service-site-id")String serviceSiteId) {
        JSONObject returnJson = super.getJsonData("API-IN-19", null);
        JSONArray deviceList = returnJson.getJSONArray("device-list");
        JSONArray newList = new JSONArray();
        for (int i = 0; i < deviceList.size(); i++) {
            JSONObject deviceJson = deviceList.getJSONObject(i);
            if (!deviceJson.getString("service-site-id").equals(serviceSiteId)) {
                newList.add(deviceJson.clone());
                continue;
            }
            JSONArray sensorList = deviceJson.getJSONArray("sensor-list");
            for (int j = 0; j < sensorList.size(); j++) {
                JSONObject sensorJson = sensorList.getJSONObject(j);
                if (sensorJson.getString("device-sensor-id").equals(deviceId)) {
                    sensorList.remove(j);
                    break;
                }
            }
            newList.add(deviceJson.clone());
        }
        returnJson.put("device-list", newList);
        super.writeJsonData("API-IN-19", null, returnJson);
    }

}
