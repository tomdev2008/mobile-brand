package com.xiu.common.web.interceptor.validator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.xiu.common.web.contants.AuthLevel;
import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.IPUtil;
import com.xiu.common.web.utils.UserSessionUtil;
import com.xiu.mobile.core.model.UserAuthInfo;
/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-4-8 上午7:00:24
 *       </p>
 **************************************************************** 
 */
@Component("cookieValidator")
public class CookieValidator implements AuthorizationValidator {
    public interface AuthValidator {
        public boolean validate(HttpServletRequest request);
    }

    public class NoneValidator implements AuthValidator {
        public boolean validate(HttpServletRequest request) {
            return true;
        }
    }

    public class UnCheckValidator implements AuthValidator {
        public boolean validate(HttpServletRequest request) {
            UserAuthInfo user = UserSessionUtil.buildUserAuthInfoFromCookie(request);
            if (user != null) {
                user.setClientIpAddress(IPUtil.getRequestIP(request));
            }
            request.setAttribute(Constants.USER_AUTH_INFO_ATTR, user);
            return true;
        }
    }

    public class BasicValidator implements AuthValidator {
        public boolean validate(HttpServletRequest request) {
            UserAuthInfo user = UserSessionUtil.buildUserAuthInfoFromCookie(request);
            if (user == null) {
                return false;
            }
            user.setClientIpAddress(IPUtil.getRequestIP(request));
            // 2. 检查是否有tokenId
            if (StringUtils.isBlank(user.getSsoTokenId()) || user.getSsoUserId() == null) {
                return false;
            }
            // 3. 检查Cookies上记录数据的安全性
            if (!UserSessionUtil.checkDataIntegrityOfUser(request)) {
                return false;
            }
            // 5. 构造UserAuthInfo对象，以便后续的业务使用
            request.setAttribute(Constants.USER_AUTH_INFO_ATTR, user);
            return true;
        }
    }

    public class StrictValidator implements AuthValidator  {
        public boolean validate(HttpServletRequest request) {
            UserAuthInfo user = UserSessionUtil.buildUserAuthInfoFromCookie(request);
            if (user == null) {
                return false;
            }
            user.setClientIpAddress(IPUtil.getRequestIP(request));
            // 2. 检查是否有tokenId
            if ((StringUtils.isBlank(user.getSsoTokenId())) || (user.getSsoUserId() == null)) {
                return false;
            }
            // 3. 检查Cookies上记录数据的安全性
            if (!UserSessionUtil.checkDataIntegrityOfUser(request)) {
                return false;
            }
            // 4. 到SSO服务器检测tokenId是否有效
            if (!checkLoginStatus(user)) {
                return false;
            }
            // 5. 构造UserAuthInfo对象，以便后续的业务使用
            request.setAttribute(Constants.USER_AUTH_INFO_ATTR, user);
            return true;
        }
    }

    protected final static XLogger LOGGER = XLoggerFactory.getXLogger(CookieValidator.class);

    protected AuthValidator getAuthValidator(AuthLevel level){
        AuthValidator validator=new NoneValidator();
        if (AuthLevel.UNCHECK == level) {
            validator=new UnCheckValidator();
        }else if (AuthLevel.BASIC == level) {
            validator=new BasicValidator();
        }else if (AuthLevel.STRICT == level) {
            validator=new StrictValidator();
        }
        return validator;
    }
    
    @Override
    public boolean validate(HttpServletRequest request, AuthLevel level) {
        Assert.notNull(level);
        return getAuthValidator(level).validate(request);
    }

    protected boolean checkLoginStatus(UserAuthInfo user) {
        return true;
    }
}
