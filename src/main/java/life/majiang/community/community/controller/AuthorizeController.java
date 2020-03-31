package life.majiang.community.community.controller;

import life.majiang.community.community.dto.AccessTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.User;
import life.majiang.community.community.provider.GithubProvider;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController{

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUri;


    @Autowired
    private GithubProvider githubProvider;

   @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name="state") String state, HttpServletRequest request) throws JSONException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        System.out.println(redirectUri);
        System.out.println("so:"+accessTokenDTO.toString());
        System.out.println(clientId);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        System.out.println("这是"+accessToken);
        String user = githubProvider.getUser(accessToken);

        GithubUser githubUser= new GithubUser();


        JSONObject jsonObject = new JSONObject(user);

        //JSONObject personObject = jsonObject.getJSONObject("plan");
        String name = "";
        int id = 0;
        try {
            name = jsonObject.getString("login");
            id = jsonObject.getInt("id");
        } catch (JSONException e) {
            System.out.println("json取不到值");
            e.printStackTrace();
        }


        githubUser.setName(name);
        githubUser.setId((long) id);
        System.out.println(id);
        if(name!=null){
            User user1 = new User();
            user1.setToken(UUID.randomUUID().toString());
            user1.setName(githubUser.getName());
            user1.setAccountId(String.valueOf(githubUser.getId()));
            user1.setGmtCreate(System.currentTimeMillis());
            user1.setGmtModified(user1.getGmtCreate());
            userMapper.insert(user1);
            request.getSession().setAttribute("user",githubUser);


            return "redirect:/";
        }else{


           return "redirect:/";
        }


        //int id = jsonObject.getInt("id");
        //String login = jsonObject.getString("login");
        //System.out.println("id:"+id+"-login:"+login);
        //githubUser.setId(personObject.getLong("id"));
        //githubUser.setName(personObject.getString("name"));

        //System.out.println("user:"+user.getName());

    }
}
