package life.majiang.community.community.controller;

import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import life.majiang.community.community.provider.GithubProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name="state") String state) throws JSONException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id("3e677354ee2bb4c4e414");
        accessTokenDTO.setClient_secret("http://127.0.0.1:8887/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret("1928d7ad34206561015fa8c0d547425799827b87");
        System.out.println("so:"+accessTokenDTO.toString());
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("这是"+accessToken);
        String user = githubProvider.getUser(accessToken);

        GithubUser githubUser= new GithubUser();

        JSONObject jsonObject = new JSONObject(user);

        JSONObject personObject = jsonObject.getJSONObject("plan");
        String result = jsonObject.getString("login");

        String name = personObject.getString("name");
        System.out.println(result);
        //int id = jsonObject.getInt("id");
        //String login = jsonObject.getString("login");
        //System.out.println("id:"+id+"-login:"+login);
        //githubUser.setId(personObject.getLong("id"));
        //githubUser.setName(personObject.getString("name"));

        //System.out.println("user:"+user.getName());
        return "index";
    }
}
