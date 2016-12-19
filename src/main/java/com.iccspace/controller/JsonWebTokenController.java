package com.iccspace.controller;


import com.iccspace.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.iccspace.mapper.MgmtUserMapper;
import com.iccspace.token.AccessToken;
import com.iccspace.token.Audience;
import com.iccspace.token.JwtHelper;
import com.iccspace.token.LoginPara;
import com.iccspace.token.MyMD5Utils;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;

/**
 * 获取token的接口，通过传入用户认证信息(用户名、密码)进行认证获取
 * 
 * @description
 * @author zhur
 * @date 2016年12月6日-上午9:20:16
 */
@RestController
public class JsonWebTokenController {
	@Autowired
	private MgmtUserMapper userMapper;

	@Autowired
	private Audience audienceEntity;

	@RequestMapping("oauth/token")
	public Object getAccessToken(@RequestBody LoginPara loginPara) {
		ResultMsg resultMsg;
		try {
			//验证clientId
			if (loginPara.getClientId() == null || (loginPara.getClientId().compareTo(audienceEntity.getClientId()) != 0)) {
				resultMsg = new ResultMsg(ResultStatusCode.INVALID_CLIENTID.getErrcode(),ResultStatusCode.INVALID_CLIENTID.getErrmsg(), null);
				return resultMsg;
			}
			// 验证用户名密码,用户名唯一
			User user = userMapper.queryMgmtUserByUserName(loginPara.getUserName());
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
			String accessToken = JwtHelper.createJWT(loginPara.getUserName(), String.valueOf(user.getUserId()),
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
			return resultMsg;
		}
	}
}