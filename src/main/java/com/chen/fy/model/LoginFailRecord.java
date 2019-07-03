package com.chen.fy.model;

import java.io.Serializable;
import java.util.Date;

public class LoginFailRecord implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 439773112476262775L;
    public int loginNum = 0;
    public Date FirstLoginTime = null;

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public Date getFirstLoginTime() {
        return FirstLoginTime;
    }

    public void setFirstLoginTime(Date firstLoginTime) {
        FirstLoginTime = firstLoginTime;
    }

}
