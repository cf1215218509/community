package life.majiang.community.community.provider;


import com.alibaba.fastjson.JSON;
import life.majiang.community.community.dto.AccessTokenDTO;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        String url = "https://github.com/login/oauth/access_token";
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String[] split = string.split("&");
                System.out.println("输出:"+string);
                String s = split[0].split("=")[1];
                System.out.println(s);
                return s;

            } catch (Exception e) {
                e.printStackTrace();
            }

        return null;
    }


    public String getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                //System.out.println(string);
                String s = JSON.toJSONString(string);
                //System.out.println(s);
                return string;

            } catch (IOException e) {
                e.printStackTrace();
            }



      /*  String url = "https://api.github.com/user?access_token="+accessToken;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            System.out.println(request);
            Response response = client.newCall(request).execute();
            System.out.println(response);
            String string = response.body().toString();
            System.out.println("出差的地方:"+string);

            JSONObject githubUser = JSON.parseObject(string);
            System.out.println(githubUser);
           // return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }*/

        return null;
    }
}
