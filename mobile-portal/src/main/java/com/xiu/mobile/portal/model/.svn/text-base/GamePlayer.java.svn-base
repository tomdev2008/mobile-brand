package com.xiu.mobile.portal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 * 游戏玩家信息
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class GamePlayer implements Serializable {

	private long idNumber; // 用户编号
	private String name; // 用户名称
	private int sex; // 用户性别
	private String nickName; // 昵称
	private int score; // 得分
	private String level; // 用户等级
	private String honorName; // 荣誉人物
	private String imgPrefix; // 域名前缀
	private String shapeImgURL; // 荣誉人物图片地址
	private String shapeDarwURL; // 荣誉人物素描地址
	private String descImgURL; // 描述方案图片地址
	private String descInfo; // 描述方案说明
	private int gains; // 战绩
	
	private List<QuestionGame> answerList = new ArrayList<QuestionGame>(); // 用户选择商品列表

	public long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(long idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getLevel() {
		if (this.getScore() >= 27) {
			return "A";
		} else if (this.getScore() >= 23) {
			return "B";
		} else if (this.getScore() >= 17) {
			return "C";
		} else {
			return "D";
		}
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getHonorName() {
		if (this.getSex() == 1) {
			if (this.getScore() >= 29) {
				return "贝克汉姆";
			} else if (this.getScore() >= 28) {
				return "布拉特.彼得";
			} else if (this.getScore() >= 27) {
				return "庄尼.德普";
			} else if (this.getScore() >= 26) {
				return "张震";
			} else if (this.getScore() >= 24) {
				return "黄秋生";
			} else if (this.getScore() >= 23) {
				return "黄晓明";
			} else if (this.getScore() >= 21) {
				return "欧弟";
			} else if (this.getScore() >= 19) {
				return "郭涛";
			} else if (this.getScore() >= 17) {
				return "李晨";
			} else if (this.getScore() >= 14) {
				return "黄渤";
			} else if (this.getScore() >= 11) {
				return "郭德纲";
			} else {
				return "王宝强";
			}
		}else{
			if (this.getScore() >= 29) {
				return "安吉丽娜朱莉";
			} else if (this.getScore() >= 28) {
				return "妮可.基德曼";
			} else if (this.getScore() >= 27) {
				return "王菲";
			} else if (this.getScore() >= 26) {
				return "舒淇";
			} else if (this.getScore() >= 24) {
				return "汤唯";
			} else if (this.getScore() >= 23) {
				return "高圆圆";
			} else if (this.getScore() >= 21) {
				return "林心如";
			} else if (this.getScore() >= 19) {
				return "张靓颖";
			} else if (this.getScore() >= 17) {
				return "周笔畅";
			} else if (this.getScore() >= 14) {
				return "谢娜";
			} else if (this.getScore() >= 11) {
				return "宋丹丹";
			} else {
				return "凤姐";
			}
		}
	}

	public void setHonorName(String honorName) {
		this.honorName = honorName;
	}

	public int getGains() {
		if (this.getSex() == 1) {
			if (this.getScore() >= 29) {
				return 99;
			} else if (this.getScore() >= 28) {
				return 97;
			} else if (this.getScore() >= 27) {
				return 95;
			} else if (this.getScore() >= 26) {
				return 84;
			} else if (this.getScore() >= 24) {
				return 77;
			} else if (this.getScore() >= 23) {
				return 75;
			} else if (this.getScore() >= 21) {
				return 61;
			} else if (this.getScore() >= 19) {
				return 58;
			} else if (this.getScore() >= 17) {
				return 41;
			} else if (this.getScore() >= 14) {
				return 33;
			} else if (this.getScore() >= 11) {
				return 27;
			} else {
				return 10;
			}
		}else{
			if (this.getScore() >= 29) {
				return 98;
			} else if (this.getScore() >= 28) {
				return 95;
			} else if (this.getScore() >= 27) {
				return 92;
			} else if (this.getScore() >= 26) {
				return 86;
			} else if (this.getScore() >= 24) {
				return 83;
			} else if (this.getScore() >= 23) {
				return 77;
			} else if (this.getScore() >= 21) {
				return 54;
			} else if (this.getScore() >= 19) {
				return 46;
			} else if (this.getScore() >= 17) {
				return 35;
			} else if (this.getScore() >= 14) {
				return 28;
			} else if (this.getScore() >= 11) {
				return 15;
			} else {
				return 7;
			}
		}
	}

	public void setGains(int gains) {
		this.gains = gains;
	}
	
	public List<QuestionGame> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<QuestionGame> answerList) {
		this.answerList = answerList;
	}

	public String getShapeImgURL() {
		// 定义中间文件地址
		String middleURL = "/m/h5questiongame/superstar/man/";
		if (this.getSex() == 1) {
			middleURL = "/m/h5questiongame/superstar/man/";
		}else{
			middleURL = "/m/h5questiongame/superstar/woman/";
		}
		// 定义图片名称
		String imgURL = "29_30.jpg";
		if (this.getScore() >= 29) {
			imgURL = "29_30.jpg";
		} else if (this.getScore() >= 28) {
			imgURL = "27_28.jpg";
		} else if (this.getScore() >= 27) {
			imgURL = "25_26.jpg";
		} else if (this.getScore() >= 26) {
			imgURL = "22_24.jpg";
		} else if (this.getScore() >= 24) {
			imgURL = "19_21.jpg";
		} else if (this.getScore() >= 23) {
			imgURL = "16_18.jpg";
		} else if (this.getScore() >= 21) {
			imgURL = "14_15.jpg";
		} else if (this.getScore() >= 19) {
			imgURL = "12_13.jpg";
		} else if (this.getScore() >= 17) {
			imgURL = "11.jpg";
		} else if (this.getScore() >= 14) {
			imgURL = "10.jpg";
		} else if (this.getScore() >= 11) {
			imgURL = "8_9.jpg";
		} else {
			imgURL = "6_7.jpg";
		}
		
		// 返回完整的路径
		return this.getImgPrefix().concat(middleURL).concat(imgURL);
	}

	public void setShapeImgURL(String shapeImgURL) {
		this.shapeImgURL = shapeImgURL;
	}

	public String getShapeDarwURL() {		
		if (this.getScore() >= 27) {
			if (this.getSex() == 1) {
				return this.getImgPrefix().concat("/m/h5questiongame/drawing/4m.png");
			}else{
				return this.getImgPrefix().concat("/m/h5questiongame/drawing/4w.png");
			}
		} else if (this.getScore() >= 23) {
			if (this.getSex() == 1) {
				return this.getImgPrefix().concat("/m/h5questiongame/drawing/3m.png");
			}else{
				return this.getImgPrefix().concat("/m/h5questiongame/drawing/3w.png");
			}
		} else if (this.getScore() >= 17) {
			if (this.getSex() == 1) {
				return this.getImgPrefix().concat("/m/h5questiongame/drawing/2m.png");
			}else{
				return this.getImgPrefix().concat("/m/h5questiongame/drawing/2w.png");
			}
		} else {
			if (this.getSex() == 1) {
				return this.getImgPrefix().concat("/m/h5questiongame/drawing/1m.png");
			}else{
				return this.getImgPrefix().concat("/m/h5questiongame/drawing/1w.png");
			}
		}
	}

	public void setShapeDarwURL(String shapeDarwURL) {
		this.shapeDarwURL = shapeDarwURL;
	}
	
	public String getDescImgURL() {
		if (this.getScore() >= 27) {
			if (this.getSex() == 1) {
				return this.getImgPrefix().concat("/m/h5questiongame/desc/mA.png");
			}else{
				return this.getImgPrefix().concat("/m/h5questiongame/desc/wA.png");
			}
		} else if (this.getScore() >= 23) {
			if (this.getSex() == 1) {
				return this.getImgPrefix().concat("/m/h5questiongame/desc/mB.png");
			}else{
				return this.getImgPrefix().concat("/m/h5questiongame/desc/wB.png");
			}
		} else if (this.getScore() >= 17) {
			if (this.getSex() == 1) {
				return this.getImgPrefix().concat("/m/h5questiongame/desc/mC.png");
			}else{
				return this.getImgPrefix().concat("/m/h5questiongame/desc/wC.png");
			}
		} else {
			if (this.getSex() == 1) {
				return this.getImgPrefix().concat("/m/h5questiongame/desc/mD.png");
			}else{
				return this.getImgPrefix().concat("/m/h5questiongame/desc/wD.png");
			}
		}
	}

	public void setDescImgURL(String descImgURL) {
		this.descImgURL = descImgURL;
	}

	public String getDescInfo() {
		if (this.getScore() >= 27) {
			if (this.getSex() == 1) {
				return "超凡品味与敏锐触觉，让你全身散发出引领风潮的气质。无论走到哪里，你都是最亮眼最有范的时尚型者！";
			}else{
				return "教主吉祥！毫无悬念时尚女神就是你。人群中，你拥有摄人的气场，超高回头率，只要亮相，就是大家追随的潮流风向！";
			}
		} else if (this.getScore() >= 23) {
			if (this.getSex() == 1) {
				return "过人的时尚品味时刻在你身上显现，或奢华；或随性，都能轻松驾控自如，是位真正懂得自己更懂打扮的潮流达人。";
			}else{
				return "毫无疑问，你是一名非常懂得驾驭衣着搭配的时尚高手。而自成一派的时尚品味，就是你打败旁人的强劲装备。";
			}
		} else if (this.getScore() >= 17) {
			if (this.getSex() == 1) {
				return "你对时尚还算有些认知，喜欢傍潮流，爱告诉人你有多会穿。心挺大，可惜内涵不足，缺乏个性是硬伤，混在人群里未必看得出来。";
			}else{
				return "只能用“不过不失”四个字来形容你的时尚品味，虽然偶尔会有超常发挥，但多数时候，你只能扮演一名不太给力的时尚模仿者。";
			}
		} else {
			if (this.getSex() == 1) {
				return "哦买嘎！你对时尚是有多么无感！什么高大上的名牌都能让你穿出矮穷挫的土渣味。赶紧学学怎么打扮，还有机会逆袭！";
			}else{
				return "我去！就你这品味只能说“相当遗憾”啦，和“时尚”二字真没一毛钱关系好吧！别总想着买这个大牌那个名牌，先上网给自己补补时尚课再说。";
			}
		}
	}

	public void setDescInfo(String descInfo) {
		this.descInfo = descInfo;
	}

	public String getImgPrefix() {
		return imgPrefix;
	}

	public void setImgPrefix(String imgPrefix) {
		this.imgPrefix = imgPrefix;
	}

	@Override
	public String toString() {
		return "GamePlayer [idNumber=" + idNumber + ", name=" + name + ", sex=" + sex + ", nickName=" + nickName 
				+ ", score=" + score + ", level=" + level + ", honorName=" + honorName + ", imgPrefix=" + imgPrefix 
				+ ", shapeImgURL=" + shapeImgURL + ", shapeDarwURL=" + shapeDarwURL + ", descImgURL="
				+ descImgURL + ", descInfo=" + descInfo + ", gains=" + gains + ", answerList=" + answerList + "]";
	}

	

}
