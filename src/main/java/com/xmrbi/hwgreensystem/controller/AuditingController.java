package com.xmrbi.hwgreensystem.controller;

import com.xmrbi.hwgreensystem.service.BaseInformationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wangfs on 2017-11-09 helloword.
 * 异常日志提交
 */

@RequestMapping("/auditing")
@Controller
public class AuditingController {
    @Autowired
    private BaseInformationService baseInformationService;

    @RequestMapping(value = "saveLog",method = RequestMethod.POST)
    public void saveLog(String crashText){
        baseInformationService.saveCrashLog(crashText);
    }
}
