package com.xmrbi.hwgreensystem.service;


import com.xmrbi.hwgreensystem.dao.util.IdUtilDao;
import com.xmrbi.hwgreensystem.until.StringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


/**
 * Created by wangfs on 2018-03-28 helloword.
 */


public class IdPool {
    private SpringContextUtil springContextUtil;


    private IdUtilDao idUtilDao;
    /**
     * 实体名称
     */
    private String entityName;
    /**
     * 表名称
     */
    private String tableName;

    /**
     * 号码池的长度
     */
    private int poolSize = 2;
    /**
     * 主键的数组
     */
    private String[] numbers;
    /**
     * 当前指针位置
     */
    private int pointer;

    public IdPool() {
    }

    public IdPool(String entityName) {
        this.entityName = entityName;
        initalize();
    }

    public IdPool(String entityName, int poolSize) {
        this.entityName = entityName;
        this.poolSize = poolSize;
        initalize();
    }

    public void initalize() {
        //从数据库取得记录
        if (idUtilDao == null) {
            this.idUtilDao = (IdUtilDao) SpringContextUtil.getBean("IdUtilDao");
        }
        long lastNumber = idUtilDao.getLastNumber(this.entityName, this.poolSize);
        //构造新的id序列
        clear();
        numbers = new String[poolSize];
        for (int i = 0; i < poolSize; i++) {
            numbers[i] = buildId(lastNumber + i);
        }
    }

    /**
     * 根据流水号生成主键
     * @param number
     * @return
     */
    private String buildId(long number) {
       return  StringUtil.getFormat(7, new Long(number).intValue());
        //return StringUtil.leftPad(number + "", 20, '0');
    }

    public synchronized String getNextId() {
        if (pointer >= poolSize) {
            clear();
            initalize();
        }
        return getIdentity();
    }


    /**
     * 返回下一个主键
     *
     * @return
     */
    private String getIdentity() {
        String nextNumber = numbers[pointer];
        pointer++;
        return nextNumber;
    }

    /**
     * 清空号码池
     */
    private void clear() {
        numbers = null;
        pointer = 0;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public String[] getNumbers() {
        return numbers;
    }

    public void setNumbers(String[] numbers) {
        this.numbers = numbers;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

 /*   public IdUtilDao getIdDao() {
        return idUtilDao;
    }

    public void setIdDao(IdUtilDao idUtilDao) {
        this.idUtilDao = idUtilDao;
    }*/

    public IdUtilDao getIdUtilDao() {
        return idUtilDao;
    }

    public void setIdUtilDao(IdUtilDao idUtilDao) {
        this.idUtilDao = idUtilDao;
    }

    public SpringContextUtil getSpringContextUtil() {
        return springContextUtil;
    }

    public void setSpringContextUtil(SpringContextUtil springContextUtil) {
        this.springContextUtil = springContextUtil;
    }
}