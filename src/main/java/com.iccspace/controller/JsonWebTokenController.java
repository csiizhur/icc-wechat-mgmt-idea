package com.iccspace.controller;


import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;
import com.iccspace.mapper.AdminMapper;
import com.iccspace.vo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.iccspace.token.AccessToken;
import com.iccspace.token.Audience;
import com.iccspace.token.JwtHelper;
import com.iccspace.token.LoginPara;
import com.iccspace.token.MyMD5Utils;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过第三方库取得验证码
 * 获取token的接口，通过传入用户认证信息(用户名、密码)进行认证获取
 * @description
 * @author zhur
 * @date 2016年12月6日-上午9:20:16
 */
@RestController
public class JsonWebTokenController {
	@Autowired
	private AdminMapper userMapper;

	@Autowired
	private Audience audienceEntity;


	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private static int captchaExpires = 3*60; //超时时间3min
	private static int captchaW = 200;
	private static int captchaH = 60;

	/**
	 * redis缓存生成的验证码
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getcaptcha", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody
	byte[] getCaptcha(HttpServletResponse response)
	{
		//生成验证码
		String uuid = UUID.randomUUID().toString();
		Captcha captcha = new Captcha.Builder(captchaW, captchaH)
				.addText().addBackground(new GradiatedBackgroundProducer())
				.gimp(new FishEyeGimpyRenderer())
				.build();

		//将验证码以<key,value>形式缓存到redis
		redisTemplate.opsForValue().set(uuid, captcha.getAnswer(), captchaExpires, TimeUnit.SECONDS);

		//将验证码key，及验证码的图片返回
		Cookie cookie = new Cookie("CaptchaCode",uuid);
		response.addCookie(cookie);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try {
			ImageIO.write(captcha.getImage(), "png", bao);
			return bao.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}


	/**
	 * {"clientId": "098f6bcd4621d373cade4e832627b4f6",
	 *	"userName": "13916176592",
	 *	"password": "111111",
	 *	"captchaCode":"de8ab66e-9af6-45c8-a8f1-eea21b294829",
	 *	"captchaValue":"pm4r8"
	 *	}
	 * 认证加上验证码
	 * @param loginPara
	 * @return
	 */
	@RequestMapping("oauth/token")
	public Object getAccessToken(@RequestBody LoginPara loginPara) {
		ResultMsg resultMsg;
		try {
			//验证码校验
			String captchaCode = loginPara.getCaptchaCode();
			if (captchaCode == null)
			{
				throw new Exception();
			}
			String captchaValue =  redisTemplate.opsForValue().get(captchaCode);
			if (captchaValue == null)
			{
				throw new Exception();
			}
			redisTemplate.delete(captchaCode);

			if (captchaValue.compareTo(loginPara.getCaptchaValue()) != 0)
			{
				throw new Exception();
			}
			//验证clientId
			if (loginPara.getClientId() == null || (loginPara.getClientId().compareTo(audienceEntity.getClientId()) != 0)) {
				resultMsg = new ResultMsg(ResultStatusCode.INVALID_CLIENTID.getErrcode(),ResultStatusCode.INVALID_CLIENTID.getErrmsg(), null);
				return resultMsg;
			}
			// 验证用户名密码,用户名唯一
			Admin user = userMapper.queryAdminInfoByMobile(loginPara.getUserName());
			if (user == null) {
				resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(),
						ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
				return resultMsg;
			} else {
				String md5Password = MyMD5Utils.getMD5(loginPara.getPassword() + "salt");
				//验证密码
				if (md5Password.compareTo(user.getPassword().toString()) != 0) {
					resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(),
							ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
					return resultMsg;
				}
			}

			// 拼装accessToken
			String accessToken = JwtHelper.createJWT(loginPara.getUserName(), String.valueOf(user.getAdminId()),
					"userRole", audienceEntity.getClientId(), audienceEntity.getName(),
					audienceEntity.getExpiresSecond() * 1000, audienceEntity.getBase64Secret());

			// 返回accessToken
			AccessToken accessTokenEntity = new AccessToken();
			accessTokenEntity.setAccess_token(accessToken);
			accessTokenEntity.setExpires_in(audienceEntity.getExpiresSecond());
			accessTokenEntity.setToken_type("bearer");
			resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),accessTokenEntity);
			return resultMsg;

		} catch (Exception ex) {
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrcode(), ResultStatusCode.SYSTEM_ERR.getErrmsg(),null);
			//验证码code
			//resultMsg = new ResultMsg(ResultStatusCode.INVALID_CAPTCHA.getErrcode(), ResultStatusCode.INVALID_CAPTCHA.getErrmsg(), null);
			return resultMsg;
		}
	}
}