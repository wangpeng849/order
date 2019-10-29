package com.studycloud.orderserver.util;

import com.studycloud.orderserver.VO.ResultVo;

public class ResultUtil {

    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(object);
        return resultVo;


    }
}
