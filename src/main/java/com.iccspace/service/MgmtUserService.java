package com.iccspace.service;

import java.util.Map;

import com.iccspace.vo.MapperJsonResponse;
import com.iccspace.vo.User;


public interface MgmtUserService {

	public User queryMgmtUser(User queryMap);
	
	public MapperJsonResponse addMgmtUser(User addMap);
}
