package com.lujinfei.deerdwmap.com.lujinfei.deerdwmap.httpfunc;

import android.util.Log;

import com.github.lujinfeifly.freejava.basic.MString;
import com.github.lujinfeifly.freejava.bean.MNameValuePair;
import com.github.lujinfeifly.freejava.http.HttpRet;
import com.github.lujinfeifly.freejava.http.MUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lujinfei on 2016/4/1.
 */
public class HttpFunc {

    private String sessionid = "";
    private String uploadToken = "";
    private String acc = "";
    private String pwd = "";

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public void setUser(String account, String passwd) {
        acc = java.net.URLEncoder.encode(account);
        pwd = java.net.URLEncoder.encode(passwd);
    }


    public HttpRet dwMaplogin(String account, String passwd) {
        HttpRet ret1 = MUrl.sendGetRequest("https://dynamic.watch/users/sign_in", "","");
        String authenticity_token ="";
        int retCode = ret1.getmRetCode();
        if(retCode == 200) {
            Document doc = Jsoup.parse(ret1.getmRetContent());
            Elements contents = doc.getElementsByAttributeValue("name", "authenticity_token");
            Element content = contents.get(0);
            authenticity_token = content.attr("value");
            if(MString.isEmpty(authenticity_token)) {  // 界面中不含token
                return new HttpRet( 508,ret1.getmRetContent());
            }
        }else {  // 返回非200
            return new HttpRet(retCode,ret1.getmRetContent());
        }

        acc = java.net.URLEncoder.encode(account);
        pwd = java.net.URLEncoder.encode(passwd);
        String token = java.net.URLEncoder.encode(authenticity_token);

        HttpRet ret =  MUrl.sendPostRequest("https://dynamic.watch/users/sign_in",
                "utf8=%E2%9C%93&authenticity_token=" + token +
                        "&user%5Bemail%5D="+acc+"&user%5Bpassword%5D="+pwd+"&user%5Bremember_me%5D=0&commit=Log+in",
                ret1.getmSessionId());
        Log.i("wwww", ret.toString());

        sessionid = ret.getmSessionId();  // 登录后重置session
        return ret;
    }

    public HttpRet dwMapGetList() {
        HttpRet ret =  MUrl.sendGetRequest("https://dynamic.watch/routes/favorites.json", "", sessionid);
        Log.i("www", ret.toString());

        if(ret.getmRetCode() == 302 && ret.getmRetContent().contains("https://dynamic.watch/users/sign_in")) { // 重新登录
            HttpRet retlogin = dwMaplogin(acc, pwd);
            if(retlogin.getmRetContent().contains("<a href=\"http://dynamic.watch/\">redirected</a>")
                    || retlogin.getmRetContent().contains("<a href=\"http://dynamic.watch/me\">redirected</a>")) {
                ret =  MUrl.sendGetRequest("https://dynamic.watch/routes/favorites.json","",  sessionid);
            }
        }

        return ret;
    }

    public HttpRet dwMapSetDefault(int id) {
        HttpRet ret =  MUrl.sendGetRequest("https://dynamic.watch/route/current/uploaded/" + id,"",  sessionid);
        Log.i("www", ret.toString());
        if(ret.getmRetCode() == 302 && ret.getmRetContent().contains("http://dynamic.watch/users/sign_in")) { // 重新登录
            HttpRet retlogin = dwMaplogin(acc, pwd);
            if(retlogin.getmRetContent().contains("<a href=\"http://dynamic.watch/\">redirected</a>")
                    || retlogin.getmRetContent().contains("<a href=\"http://dynamic.watch/me\">redirected</a>")) {
                ret =  MUrl.sendGetRequest("http://dynamic.watch/route/current/uploaded/" + id,"",  sessionid);
            }
        }

        return ret;
    }

    public HttpRet dwMapLinkWatchPre() {
        HttpRet ret =  MUrl.sendGetRequest("https://dynamic.watch/link_codes/new","",  sessionid);
        Log.i("www", ret.toString());
        if(MString.isNotEmpty(ret.getmSessionId())) {
            sessionid = ret.getmSessionId();
        }

        return ret;
    }

    public HttpRet dwMapLinkWatch(String valid, String code) {
        String va = java.net.URLEncoder.encode(valid);
        String co = java.net.URLEncoder.encode(code);
        HttpRet ret =  MUrl.sendPostRequest("https://dynamic.watch/link_codes",
                "utf8=%E2%9C%93&authenticity_token="+va+"&link_code%5Bcode%5D="+co+"&commit=Link+Watch",
                sessionid);
        Log.i("www", ret.toString());

        if(MString.isNotEmpty(ret.getmSessionId())) {
            sessionid = ret.getmSessionId();
        }

        return ret;
    }

    public HttpRet dwMapUpload(File file) {

        Log.i("www*****************", uploadToken);
        if(MString.isEmpty(uploadToken)) {
            HttpRet ret5 = MUrl.sendGetRequest("https://dynamic.watch/routes/new", "", sessionid);
            Log.i("www", ret5.toString());
            if (ret5.getmRetCode() == 200) {
                Document doc = Jsoup.parse(ret5.getmRetContent());
                Elements contents = doc.getElementsByAttributeValue("name", "authenticity_token");
                Element content = contents.get(0);
                uploadToken = content.attr("value");
            }

            if(MString.isNotEmpty(ret5.getmSessionId())) {
                sessionid = ret5.getmSessionId();
            }
        }

        MNameValuePair a = new MNameValuePair("utf8",  new String(MString.parseHexStr2Byte("E29C93")));
        MNameValuePair b = new MNameValuePair("authenticity_token", uploadToken);
        List<MNameValuePair> param = new ArrayList<MNameValuePair>();
        param.add(a);
        param.add(b);
        HttpRet ret6 = MUrl.uploadFile("https://dynamic.watch/routes/upload", param, file, sessionid);
        Log.i("www", ret6.toString());

        if(MString.isNotEmpty(ret6.getmSessionId())) {
            sessionid = ret6.getmSessionId();
        }

        return ret6;
    }

}
