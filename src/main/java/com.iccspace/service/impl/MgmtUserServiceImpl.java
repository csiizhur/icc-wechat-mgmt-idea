package com.iccspace.service.impl;

import com.iccspace.vo.MapperJsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iccspace.core.Constants;
import com.iccspace.mapper.MgmtUserMapper;
import com.iccspace.vo.User;

import com.iccspace.service.MgmtUserService;

@Service
public class MgmtUserServiceImpl implements MgmtUserService{

	private Logger log=LoggerFactory.getLogger(getClass());
	@Autowired
    private MgmtUserMapper userMapper;

	@Override
	public User queryMgmtUser(User queryMap) {
		User result = userMapper.queryMgmtUser(queryMap);
		return result;
	}

	@Override
	public MapperJsonResponse addMgmtUser(User addMap) {
		MapperJsonResponse re=new MapperJsonResponse();
		int result = userMapper.insertMgmtUsers(addMap);
		if(result>0){
			re.setCode(Constants.SUCCESS_CODE);
			re.setMessage(Constants.ADD_DATA_SUCCESS_MESSAGE);
		}else{
			re.setCode(Constants.ADD_DATA_ERROR_CODE);
			re.setMessage(Constants.ADD_DATA_ERROR_MESSAGE);
		}
		return re;
	}
}
