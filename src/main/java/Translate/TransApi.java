package Translate;

import java.util.HashMap;
import java.util.Map;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private final String appidd;
    private String securityKey;

    public TransApi(String appidd, String securityKey) {
        this.appidd = appidd;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) throws Exception {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appidd);


        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);


        String src = appidd + query + salt + securityKey;
        params.put("sign", MD5.md5(src));

        return params;
    }

}
