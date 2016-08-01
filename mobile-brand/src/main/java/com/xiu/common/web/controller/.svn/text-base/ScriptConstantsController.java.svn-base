package com.xiu.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiu.mobile.core.constants.ErrConstants;

/**
  * @ClassName: GenScriptConstantsController
  * @Description: 动态生成JS常量
  * @author toney.li
  * @date 2012-4-28 下午2:59:12
  *
  */
@Controller
@RequestMapping(value="/script")
public class ScriptConstantsController {
	
	@RequestMapping(value="constants",method=RequestMethod.GET)
	public String genScriptConstants(Model model){
		model.addAttribute("errCode", ErrConstants.HttpErrorCode.errorCodeMap);
		return "template/js_constants";
	}

}
