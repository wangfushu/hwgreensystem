package com.xmrbi.hwgreensystem.until.treeUtils;

import com.google.common.collect.Lists;
import com.xmrbi.hwgreensystem.domain.db.SysPlaza;

import java.util.ArrayList;
import java.util.List;

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
 * @Date: 2018-09-20 11:53
 */
public class TreeListUtils {
    static  List<Long> child=new ArrayList<Long>();

    public TreeListUtils() {
        child.clear();
    }

    public static List<Long> treeSysPlazaList(List<SysPlaza> list, Long pid){
        for(SysPlaza sysPlaza: list){
            //遍历出父id等于参数的id，add进子节点集合
            if(sysPlaza.getParentId().equals(pid)){
                //递归遍历下一级
                child.add(sysPlaza.getPlazaId());
                treeSysPlazaList(list,sysPlaza.getPlazaId());
            }
        }
        return child;
    }

}
