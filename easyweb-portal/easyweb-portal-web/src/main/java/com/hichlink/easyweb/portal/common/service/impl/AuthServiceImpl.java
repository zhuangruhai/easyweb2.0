package com.hichlink.easyweb.portal.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hichlink.easyweb.portal.common.cache.AuthCache;
import com.hichlink.easyweb.portal.common.dao.OperationAddressDao;
import com.hichlink.easyweb.portal.common.dao.OperationDao;
import com.hichlink.easyweb.portal.common.entity.Operation;
import com.hichlink.easyweb.portal.common.entity.OperationAddress;
import com.hichlink.easyweb.portal.common.service.AuthService;

@Service("authService")
public class AuthServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	@Autowired
	private OperationDao operationDao;

	@Autowired
	private OperationAddressDao operationAddressDao;

	private static final String REG_SEP = "{*}";

	public Map<String, String> listAddressUrlByLoginName(String loginName) throws Exception {

		List<OperationAddress> list = operationAddressDao.listOperationAddressByLoginName(loginName);

		return createMap(list);
	}

	public boolean authorizeSuccess(String addressUrl, Map<String, String> urlMap) {
		String strs[] = addressUrl.split("\\?");

		// 找不到对应的url，鉴权失败
		if (!isContains(urlMap.keySet(), strs[0]))
			return false;

		String neededAuth = urlMap.get(strs[0]);

		// 如果不需要对url参数鉴权，返回成功鉴权成功
		if (neededAuth == null || strs.length < 2)
			return true;

		// url带的参数鉴权，如果有的话

		Set<String> urlParams = splitParam(strs[1]);

		Set<String> neededAuthParams = splitParam(neededAuth);

		// 如果被鉴权的url全部包含需要鉴权的参数，返回成功
		// 否则返回失败
		return urlParams.containsAll(neededAuthParams);
	}

	public boolean authorizeFail(String addressUrl, Map<String, String> urlMap) {
		return !authorizeSuccess(addressUrl, urlMap);
	}

	/**
	 * 判断是否鉴权例外的url
	 * 
	 * @param addressUrl
	 * @return
	 */
	public boolean authExclude(String addressUrl) {

		initCache();

		Map<String, String> excludeAuthMap = AuthCache.getCache().getUnauthMap();
		if (excludeAuthMap != null) {
			return isContains(excludeAuthMap.keySet(), addressUrl);
		}

		return false;
	}

	/**
	 * 判断是否登录后不需要鉴权的url
	 * 
	 * @param addressUrl
	 * @return
	 */
	public boolean notNeedAuthAfterLogin(String addressUrl) {
		initCache();

		Map<String, String> loginAuthMap = AuthCache.getCache().getLoginAuthMap();

		if (loginAuthMap != null) {
			return isContains(loginAuthMap.keySet(), addressUrl);
		}

		return false;
	}

	/**
	 * url鉴权
	 */
	public boolean authorize(Long staffId, String url) {

		initCache();

		// 先判断是否不需要鉴权的url
		String strs[] = url.split("\\?");
		String addressUrl = strs[0];
		String addressParam = strs.length > 1 ? strs[1] : null;

		// 用户级鉴权url, 每个url可能对应有多条记录
		Map<String, List<OperationAddress>> authMap = AuthCache.getCache().getAuthMap();

		// 如果没有对应的url，返回成功鉴权失败
		StringBuilder sb = new StringBuilder();
		if (!isContains(authMap.keySet(), addressUrl, sb)) {
			logger.debug("method[authorize],addressUrl=" + addressUrl + ",失败");
			return false;
		}

		// 找到用户所有的操作权限
		Set<String> userOperationList = getResourceAndOperationKey(staffId);
		String addressUrlTmp = addressUrl;
		if (null != sb && !"".equals(sb.toString())) {
			addressUrlTmp = sb.toString();
		}
		List<OperationAddress> neededAuthList = authMap.get(addressUrlTmp);

		// 没有找到用户对应需要鉴权的url，直接返回失败
		if (neededAuthList == null || neededAuthList.size() == 0) {
			return false;
		}
		// 循环比对用户所拥有的操作权限
		for (OperationAddress address : neededAuthList) {
			// 如果找到用户的权限对应的url,再比对参数,
			// 如果所有的url和参数匹配不成功，则鉴权失败
			logger.debug(address.getResourceId() + "-" + address.getOperationKey());
			if (userOperationList.contains(address.getResourceId() + "-" + address.getOperationKey())) {
				// 没有参数, 直接返回
				if (addressParam == null || "".equals(addressParam)) {
					return true;
				}

				// 有参数, 匹配参数
				if (containsAuthParam(addressParam, buildParamString(address))) {
					return true;
				}
			}
		}

		return false;
	}

	private Set<String> splitParam(String params) {
		Set<String> set = new HashSet<String>();

		if (params == null) {
			return set;
		}

		// 拆分URL和参数（根据&号拆分）
		String[] strs = params.split("\\&");

		for (String s : strs) {
			set.add(s);
		}

		return set;
	}

	private boolean containsAuthParam(String targetParam, String neededParam) {
		Set<String> urlParams = splitParam(targetParam);

		Set<String> neededAuthParams = splitParam(neededParam);

		// 如果被鉴权的url全部包含需要鉴权的参数，返回成功
		// 否则返回失败
		return urlParams.containsAll(neededAuthParams);
	}

	private synchronized void initCache() {

		if (AuthCache.getCache().getUnauthMap() == null) {
			List<OperationAddress> unAuthList = operationAddressDao.listOperationAddressByAuthType("UNAUTH");

			List<OperationAddress> loginAuthList = operationAddressDao.listOperationAddressByAuthType("LOGIN_AUTH");

			List<OperationAddress> authList = operationAddressDao.listOperationAddressByAuthType("AUTH");

			AuthCache.getCache().init(createMap(unAuthList), createMap(loginAuthList), createAuthMap(authList));
		}
	}

	private Set<String> getResourceAndOperationKey(Long staffId) {
		List<Operation> list = operationDao.listResourceOperationByStaffId(staffId);

		Set<String> set = new HashSet<String>();

		for (Operation record : list) {
			set.add(record.getResourceId() + "-" + record.getOperationKey());
		}

		return set;
	}

	private Set<String> getResourceKeyAndOperationKey(Long staffId) {
		List<Operation> list = operationDao.listResourceKeyAndOperationKeyStaffId(staffId);

		Set<String> set = new HashSet<String>();

		for (Operation record : list) {
			set.add(record.getResourceKey() + "-" + record.getOperationKey());
		}
		return set;
	}

	private Map<String, String> createMap(List<OperationAddress> list) {
		Map<String, String> urlMap = new HashMap<String, String>();

		for (OperationAddress address : list) {

			urlMap.put(address.getOperationAddressUrl(), buildParamString(address));
		}

		return urlMap;
	}

	private Map<String, List<OperationAddress>> createAuthMap(List<OperationAddress> list) {
		Map<String, List<OperationAddress>> authMap = new HashMap<String, List<OperationAddress>>();

		List<OperationAddress> tempList = null;
		for (OperationAddress address : list) {
			if (authMap.containsKey(address.getOperationAddressUrl())) {
				tempList = authMap.get(address.getOperationAddressUrl());

				tempList.add(address);
			} else {
				tempList = new ArrayList<OperationAddress>();

				tempList.add(address);
				authMap.put(address.getOperationAddressUrl(), tempList);
			}
		}

		return authMap;
	}

	private String buildParamString(OperationAddress address) {
		StringBuffer paramStr = new StringBuffer();

		if (!StringUtils.isEmpty(address.getParameterName1())) {
			paramStr.append(address.getParameterName1()).append("=").append(address.getParameterValue1());
		}

		if (!StringUtils.isEmpty(address.getParameterName2())) {
			paramStr.append("&").append(address.getParameterName2()).append("=").append(address.getParameterValue2());
		}

		if (!StringUtils.isEmpty(address.getParameterName3())) {
			paramStr.append("&").append(address.getParameterName3()).append("=").append(address.getParameterValue3());
		}

		return paramStr.toString();
	}

	public List<Map<String, Boolean>> authorize(Long staffId, String[] resKeys, String[] operKeys) {
		List<Map<String, Boolean>> results = new ArrayList<Map<String, Boolean>>();
		// 找到用户所有的操作权限
		Set<String> userOperationList = getResourceKeyAndOperationKey(staffId);
		for (int i = 0; i < resKeys.length; i++) {
			Map<String, Boolean> result = new HashMap<String, Boolean>();
			if (userOperationList.contains(resKeys[i] + "-" + operKeys[i])) {
				result.put("result", true);
			} else {
				result.put("result", false);
			}
			results.add(result);
		}
		return results;
	}

	private boolean isMatch(String reg, String str) {
		String[] regs = reg.split("\\{\\*\\}");
		int len = regs.length;
		if (len == 1) {
			if (str.startsWith(regs[0])) {
				return true;
			} else {
				return false;
			}
		} else if (len == 2) {
			int start = str.indexOf(regs[0]);
			int end = str.lastIndexOf(regs[1]);
			if (start + regs[0].length() <= end && str.startsWith(regs[0]) && str.endsWith(regs[1]))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	private boolean isContains(Set<String> keys, String url, StringBuilder sb) {
		if (keys.contains(url)) {
			return true;
		} else {
			for (String key : keys) {
				if (key.indexOf(REG_SEP) > -1) {
					if (isMatch(key, url)) {
						if (null != sb) {
							sb.append(key);
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isContains(Set<String> keys, String url) {
		return isContains(keys, url, null);
	}
}
