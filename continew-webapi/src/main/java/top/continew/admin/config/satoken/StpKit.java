package top.continew.admin.config.satoken;

import cn.dev33.satoken.stp.StpLogic;

public class StpKit {

    /**
     * Api 会话对象，管理 Api 接口所有账号的登录、权限认证
     */
    public static final StpLogic API = new StpLogic("api");
}
