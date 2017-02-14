package com.xmg.p2p;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Panda on 2016/10/31.
 */
@Controller
public class M5CSendController {
    @RequestMapping("send")
    @ResponseBody
    public String send(String username, String password, String apikey, String mobile, String content) {
        System.out.println("假装发短信给" + mobile + ", 短信内容为:" + content);
        return "success:发送成功";

    }
}
