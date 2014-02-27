package main.java.com.check.rest.validator;

import com.check.model.dto.LoginInfo;
import com.check.model.dto.RegisterInfo;
import com.check.model.dto.SocialInfo;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.utils.CheckUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 17.02.14
 * Time: 23:31
 * To change this template use File | Settings | File Templates.
 */
public class UserValidator {

    public static void validateLogin(LoginInfo loginInfo) throws Exception {
        if (loginInfo == null || CheckUtils.isEmpty(loginInfo.getLogin()) || CheckUtils.isEmpty(loginInfo.getPasswordHash())) {
            throw new Exception(ErrorCodes.INCORRECT_DATA);
        }
    }

    public static void validateSocialRegister(SocialInfo socialInfo) throws Exception {
        if (socialInfo == null || CheckUtils.isEmpty(socialInfo.getUserName()) || CheckUtils.isEmpty(socialInfo.getSocialType())) {
            throw new Exception(ErrorCodes.INCORRECT_DATA);
        }
    }

    public static void validateRegsiter(RegisterInfo registerInfo) throws Exception {
        if (registerInfo == null || CheckUtils.isEmpty(registerInfo.getLogin()) || CheckUtils.isEmpty(registerInfo.getPasswordHash()) || CheckUtils.isEmpty(registerInfo.getEmail())) {
            throw new Exception(ErrorCodes.INCORRECT_DATA);
        }
    }
}
