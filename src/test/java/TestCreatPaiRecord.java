import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chen.fy.controller.business.check.WaitCheckService;
import com.jfinal.config.Constants;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.plugin.IPlugin;

public class TestCreatPaiRecord {
    private Constants constants;
    private Plugins plugins;

    /**
     * 通过配置类启动jfinal插件等
     */
    @Before
    public void initConfig() {
        try {
            String configClass = "com.jfinal.club.common.JFinalClubConfig";
            Class<?> clazz = Class.forName(configClass);
            JFinalConfig jfinalConfig = (JFinalConfig)clazz.newInstance();
            constants = new Constants();
            jfinalConfig.configConstant(constants);
            plugins = new Plugins();
            jfinalConfig.configPlugin(plugins);
            for (IPlugin plug : plugins.getPluginList()) {
                plug.start();
            }
            jfinalConfig.afterJFinalStart();
            System.out.println("\n==JunitFinalTest Start==================\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止jfinal插件
     */
    @After
    public void endConfig() {
        System.out.println("\n==JunitFinalTest End====================");
        try {
            if (plugins != null) {
                for (IPlugin plug : plugins.getPluginList()) {
                    plug.stop();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Test
    public void doTest() {
        System.out.println(" hello ");
        WaitCheckService servcie = WaitCheckService.me;

    }
}
