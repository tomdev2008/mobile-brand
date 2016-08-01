package com.xiu.mobile.portal.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.model.GamePlayer;
import com.xiu.mobile.portal.model.QuestionGame;
import com.xiu.mobile.portal.model.QuestionPlayer;
import com.xiu.mobile.portal.service.IQuestionGameService;
import com.xiu.mobile.portal.service.IQuestionPlayerService;

/***
 * 用户游戏
 * @author hejianxiong
 *
 */
@Controller
@RequestMapping("/questionGame")
public class QuestionGameController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionGameController.class);
	
	@Autowired
	private IQuestionGameService questionGameService;
	@Autowired
	private IQuestionPlayerService questionPlayerService;
	
	/***
	 * 用户选择商品信息
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/init", produces = "text/html;charset=UTF-8")
	public String init(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			List<QuestionPlayer> questionPlayers = questionPlayerService.getList();
			logger.error("初始化游戏数据信息 questionPlayers="+questionPlayers);
			if (questionPlayers.size()>0) {
				QuestionPlayer questionPlayer = questionPlayers.get(0);
				Long maxNumber = questionPlayer.getMaxNumber();
				int sex = NumberUtils.toInt(request.getParameter("sex"), 0);
				// 更新游戏玩家统计数据信息
				Random random = new Random();
				// 产生一个1-10的随机数字 更新到游戏玩家统计数据中
				int randomNumber = random.nextInt(10) + 1;
				Long total = maxNumber + randomNumber;
				questionPlayer.setMaxNumber(total);
				questionPlayer.setReallyNumber(questionPlayer.getReallyNumber()+1);
				// 更新到数据库
				questionPlayerService.updateQuestionPlayer(questionPlayer);
				// 封装游戏玩家信息
				GamePlayer gamePlayer = new GamePlayer();
				gamePlayer.setIdNumber(maxNumber);
				gamePlayer.setSex(sex);
				gamePlayer.setImgPrefix("http://6.xiustatic.com");
				HttpSession session = request.getSession();
				session.setAttribute("gamePlayer", gamePlayer);
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
				result.put("gamePlayer", gamePlayer);
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				logger.error("初始化游戏信息出错：questionPlayerService.getList()没对应数据信息 questionPlayers="+questionPlayers);
			}
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("初始化游戏信息出错：", e);
		}
		logger.info("初始化游戏信息返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 用户获取选择商品信息
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getQuestions", produces = "text/html;charset=UTF-8")
	public String getQuestions(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("gamePlayer")==null) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.NotChooseSex.getErrorCode());
				result.put("errorMsg", ErrorCode.NotChooseSex.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			int questionNo = NumberUtils.toInt(request.getParameter("number"), 1);
			if (questionNo<1 || questionNo > 6) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				result.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			boolean hasNext = questionNo < 6 ? true : false;
			Map<String, Object> params = new HashMap<String, Object>();
			GamePlayer gamePlayer = (GamePlayer) session.getAttribute("gamePlayer");
			params.put("sex", gamePlayer.getSex());
			params.put("questionNo", questionNo);
			String question = getQuestion(gamePlayer.getSex(), questionNo);
			List<QuestionGame> questionList = questionGameService.getListByParams(params);
			// 随机排序
			Collections.shuffle(questionList);
			result.put("question", question);
			result.put("questionList", questionList);
			result.put("sex", gamePlayer.getSex());
			result.put("questionNo", questionNo);
			result.put("hasNext", hasNext);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户获取选择商品信息异常：", e);
		}
		logger.info("用户获取选择商品信息返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}

	/***
	 * 用户选择商品信息
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/chooseGoods", produces = "text/html;charset=UTF-8")
	public String chooseGoods(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("gamePlayer")==null) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.NotChooseSex.getErrorCode());
				result.put("errorMsg", ErrorCode.NotChooseSex.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String id = request.getParameter("id");
			if (StringUtils.isBlank(id)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 获取游戏玩家信息
			GamePlayer gamePlayer = (GamePlayer) session.getAttribute("gamePlayer");
			List<QuestionGame> answerList = gamePlayer.getAnswerList();
			if (answerList == null) {
				answerList = new ArrayList<QuestionGame>();
			}
			// 获取选择的游戏信息
			QuestionGame questionGame = questionGameService.getById(Long.parseLong(id));
			String imgURL = questionGame.getImgURL();
			// 问题图片和答案图片地址不一致
			imgURL = imgURL.replace("question_items", "answer_items");
			questionGame.setImgURL(imgURL);
			answerList.add(questionGame);
			gamePlayer.setAnswerList(answerList);
			session.setAttribute("gamePlayer", gamePlayer);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("玩家选择商品异常：", e);
		}
		logger.info("用户选择商品信息返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/***
	 * 用户获取最终分数
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getScore", produces = "text/html;charset=UTF-8")
	public String getScore(String jsoncallback, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("gamePlayer")==null) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.NotChooseSex.getErrorCode());
				result.put("errorMsg", ErrorCode.NotChooseSex.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			// 获取玩家信息
			GamePlayer gamePlayer = (GamePlayer) session.getAttribute("gamePlayer");
			List<QuestionGame> answerList = gamePlayer.getAnswerList();
			if (answerList == null || answerList.size() < 6) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.ChooseGoodsNotFinish.getErrorCode());
				result.put("errorMsg", ErrorCode.ChooseGoodsNotFinish.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			int total = 0;
			for (QuestionGame questionGame : answerList) {
				total = questionGame.getScore() + total;
			}
			gamePlayer.setScore(total);
			session.removeAttribute("gamePlayer");
			result.put("gamePlayer", gamePlayer);
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取玩家分数异常：", e);
		}
		logger.info("获取玩家分数信息返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 通过题号和性别获取问题内容
	 * @param sex
	 * @param questionNo
	 * @return
	 */
	private String getQuestion(int sex,int questionNo){
		String question = "";
		if (sex == 1) {
			switch (questionNo) {
			case 1:
				question = "和她的周年约会，你会选择哪件服装？";
				break;
			case 2:
				question = "参加同学聚会，你会选择哪条裤子？";
				break;
			case 3:
				question = "休闲压马路，你会选择哪双鞋子？";
				break;
			case 4:
				question = "在夜店欢畅时刻，你会选择哪顶帽子？";
				break;
			case 5:
				question = "与老板外出见客户，你会选择哪款包包？";
				break;
			default:
				question = "出席商务酒会，你会选择哪款手表？";
				break;
			}
		}else{
			switch (questionNo) {
			case 1:
				question = "与闺蜜下午茶，你会选择哪件衣服？";
				break;
			case 2:
				question = "赴约盛大宴会，你会选择哪条裙子？";
				break;
			case 3:
				question = "面试新工作，你会选择哪双鞋子？";
				break;
			case 4:
				question = "和爱人浪漫假期，你会选择哪顶帽子";
				break;
			case 5:
				question = "第一次见家长，你会选择哪件饰品？";
				break;
			default:
				question = "出席生日派对，你会选择哪款包包？";
				break;
			}
		}
		
		return question;
	}
}
