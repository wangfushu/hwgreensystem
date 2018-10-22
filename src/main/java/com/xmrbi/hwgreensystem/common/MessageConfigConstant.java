package com.xmrbi.hwgreensystem.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 *
 * @description :
 * ---------------------------------
 * @Author: wangfushu
 * @Date: 2018-09-10 17:44
 */
public class MessageConfigConstant {
    private static Logger logger = LoggerFactory.getLogger(MessageConfigConstant.class);
    private static Properties props;
    static{
        loadProps();
    }
    //演示系统账户
    public static String DEMO_ACCOUNT = "test";
    //自动去除表前缀
    public static String AUTO_REOMVE_PRE = "true";
    //停止计划任务
    public static String STATUS_RUNNING_STOP = "stop";
    //开启计划任务
    public static String STATUS_RUNNING_START = "start";
    //通知公告阅读状态-未读
    public static String OA_NOTIFY_READ_NO = "0";
    //通知公告阅读状态-已读
    public static int OA_NOTIFY_READ_YES = 1;
    //部门根节点id
    public static Long DEPT_ROOT_ID = 0l;
    //部门根节点名称
    public static String DEPT_ROOT_NAME = "最高级节点";


    //接口验证码 防止别人直接访问接口
    public static String AUTH_CODE;
    //异常报警邮箱
    public static String EMAIL_ADDRESS;

    //自定义表主键的数据库驱动
    public static String IDUTIL_DRIVER;
    //自定义表主键的数据库地址
    public static String IDUTIL_URL;
    //自定义表主键的数据库用户名
    public static String IDUTIL_USERNAME;
    //自定义表主键的数据库用户密码
    public static String IDUTIL_PASSWORD;

    public static String image_base_path;
    public static String image_file_name;


    synchronized static private void loadProps(){
        logger.info("加载配置文件内容");
        props = new Properties();
        InputStream in = null;
        try {
            in = MessageConfigConstant.class.getClassLoader().getResourceAsStream("constant.properties");
            props.load(in);
            IDUTIL_DRIVER=props.getProperty("IDUTIL_DRIVER");
            IDUTIL_URL=props.getProperty("IDUTIL_URL");
            IDUTIL_USERNAME=props.getProperty("IDUTIL_USERNAME");
            IDUTIL_PASSWORD=props.getProperty("IDUTIL_PASSWORD");

            image_base_path=props.getProperty("image_base_path");
            image_file_name=props.getProperty("image_file_name");
        } catch (Exception e) {
            logger.error("加载失败constant.properties "+e.getMessage());
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (Exception e) {
                logger.error("xai-constant.properties文件流关闭出现异常"+e.getMessage());
            }
        }
    }
}
