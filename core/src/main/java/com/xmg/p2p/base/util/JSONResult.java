package com.xmg.p2p.base.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JSONResult {
    private String msg;
    private boolean success = true;

    public JSONResult(String msg) {
        this.msg = msg;
    }

    public JSONResult(String msg, boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public JSONResult() {
    }
}
