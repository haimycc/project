package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Panda on 2016/10/28.
 */
@Getter
@Setter
public class SystemDictionaryItem extends BaseDomain {
    private String title;//字典明细显示名称

    private String intro;//字典明细使用说明

    private String sequence;//字典明细在该分类中的排序

    private Long parentId;//字典明细对应的分类id

}
