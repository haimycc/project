package com.xmg.p2p.website;

import com.xmg.p2p.base.domain.UserInfo;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.service.IUserInfoService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Panda on 2016/11/1.
 */
@Controller
public class BasicInfoController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("basicInfo")
    public String basicInfo(Model model) {
        model.addAttribute("userinfo", userInfoService.getCurrent());
        model.addAttribute("educationBackgrounds", systemDictionaryService.getListBySn("educationBackground"));
        model.addAttribute("incomeGrades", systemDictionaryService.getListBySn("incomeGrade"));
        model.addAttribute("marriages", systemDictionaryService.getListBySn("marriage"));
        model.addAttribute("kidCounts", systemDictionaryService.getListBySn("kidCount"));
        model.addAttribute("houseConditions", systemDictionaryService.getListBySn("houseCondition"));
        return "userInfo";
    }

    @RequestMapping("basicInfo_save")
    @ResponseBody
    public JSONResult saveBasicInfo(UserInfo userinfo) {
        userInfoService.saveBasicInfo(userinfo);
        return new JSONResult();
    }

}
