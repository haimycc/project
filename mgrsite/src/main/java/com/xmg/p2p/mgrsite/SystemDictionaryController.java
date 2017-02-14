package com.xmg.p2p.mgrsite;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Panda on 2016/11/1.
 */
@Controller
public class SystemDictionaryController {
    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("systemDictionary_list")
    public String systemDictionaryList(@ModelAttribute("qo") SystemDictionaryQueryObject qo, Model model) {
        model.addAttribute("pageResult", systemDictionaryService.queryDics(qo));
        return "systemdic/systemDictionary_list";
    }


    /**
     * 添加或者修改数据字典分类
     */
    @RequestMapping("systemDictionary_update")
    @ResponseBody
    public JSONResult systemDictionarySaveOrUpdate(SystemDictionary dic) {
        systemDictionaryService.saveOrUpdate(dic);
        return new JSONResult();
    }

    /**
     * 数据字典明细
     */
    @RequestMapping("systemDictionaryItem_list")
    public String itemList(Model model, @ModelAttribute("qo") SystemDictionaryQueryObject qo) {
        model.addAttribute("pageResult", systemDictionaryService.queryItem(qo));
        model.addAttribute("systemDictionaryGroups", systemDictionaryService.listAll());
        return "systemdic/systemDictionaryItem_list";
    }

    /**
     * 保存数据字典明细
     */
    @RequestMapping("systemDictionaryItem_update")
    @ResponseBody
    public JSONResult itemSaveOUpdate(SystemDictionaryItem item) {
        systemDictionaryService.itemSaveOrUpdate(item);
        return new JSONResult();
    }

    /**
     * 修改数据字典明细的回显
     */
    @RequestMapping("systemDictionaryItem_edit")
    @ResponseBody
    public SystemDictionaryItem itemEdit(Long id) {
        SystemDictionaryItem item = systemDictionaryService.selectByPrimaryKey(id);
        return item;
    }
}
