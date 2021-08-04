package cn.jxh.learning.swagger.controller;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.jxh.learning.swagger.utils.CommonUtils;

public class BaseController {

    @Value(("${file.data-path}"))
    private String jsonFilePath;

    public JSONObject getJsonData(String apiName, String suffix) {
        Path currentPath;
        if (StringUtils.isEmpty(suffix)) {
            currentPath = Paths.get(jsonFilePath, apiName + ".json");
        } else {
            currentPath = Paths.get(jsonFilePath, apiName + "_" + suffix + ".json");
        }
        
        return CommonUtils.readJsonFile(currentPath);
    }

    public void writeJsonData(String apiName, String suffix, JSONObject obj) {
        Path currentPath;
        if (StringUtils.isEmpty(suffix)) {
            currentPath = Paths.get(jsonFilePath, apiName + ".json");
        } else {
            currentPath = Paths.get(jsonFilePath, apiName + "_" + suffix + ".json");
        }

        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(currentPath.toString()),"UTF-8");
            String jsonStr = JSON.toJSONString(obj, SerializerFeature.PrettyFormat);
            osw.write(jsonStr);
            osw.flush();
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
