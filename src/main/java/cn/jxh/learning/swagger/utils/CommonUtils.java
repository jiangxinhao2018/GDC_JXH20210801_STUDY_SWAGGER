package cn.jxh.learning.swagger.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtils {

    /**
     * 32桁トークン生成
     *
     * @return
     */
    public static String generateToken() {
        // UUIDベースでトークン生成
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 36桁リクエストID生成
     * <br>
     * 例：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     * @return
     */
    public static String generateRequestId() {
        return UUID.randomUUID().toString();
    }

    public static JSONObject readJsonFile(Path filePath) {
        log.debug(filePath.toString());
        BufferedReader reader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath.toString());
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            String readJson = "";
            while ((tempString = reader.readLine()) != null) {
                readJson += tempString;
            }
            return JSONObject.parseObject(readJson);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new JSONObject();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

}
