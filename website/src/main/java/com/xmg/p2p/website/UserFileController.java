package com.xmg.p2p.website;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.UploadUtil;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * Created by Panda on 2016/11/3.
 */
@Controller
public class UserFileController {

    @Autowired
    private IUserFileService userFileService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("userFile")
    public String userfiles(Model model) {
        //数据字典
        model.addAttribute("fileTypes", systemDictionaryService.getListBySn("userFileType"));
        //查询出当前用户没有选择分类的对象
        List<UserFile> userFiles = userFileService.getSelectList(UserContext.getCurrent().getId(), false);
        if (userFiles.size() > 0) {
            model.addAttribute("userFiles", userFiles);
            return "userFiles_commit";
        } else {
            userFiles = userFileService.getSelectList(UserContext.getCurrent().getId(), true);
            model.addAttribute("userFiles", userFiles);
        }
        return "userfiles";
    }

    /**
     * 上传图片
     *
     * @param image
     * @return
     */
    @RequestMapping("userFileUpload")
    @ResponseBody
    public String userFileUpload(MultipartFile image) {
        String fileName = UploadUtil.upload(image, servletContext.getRealPath("/userfile"));
        //将图片名保存到资料对象中
        userFileService.apply("/userfile/" + fileName);
        return "/userfile/" + fileName;
    }

    /**
     * 保存分类信息
     */
    @RequestMapping("userFile_selectType")
    @ResponseBody
    public JSONResult saveFileType(Long[] id, Long[] fileType) {
        userFileService.saveFileType(id, fileType);
        return new JSONResult();
    }
}

