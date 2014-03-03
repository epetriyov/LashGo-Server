package main.java.com.check.rest.validator;

import com.check.model.dto.LoginInfo;
import com.check.model.dto.RegisterInfo;
import com.check.model.dto.SocialInfo;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.ValidationException;
import main.java.com.check.utils.CheckUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 17.02.14
 * Time: 23:31
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserValidator {

    public void validateLogin(LoginInfo loginInfo) throws ValidationException {
        if (loginInfo == null || CheckUtils.isEmpty(loginInfo.getLogin()) || CheckUtils.isEmpty(loginInfo.getPasswordHash())) {
            throw new ValidationException(ErrorCodes.INCORRECT_DATA);
        }
    }

    public void validateSocialRegister(SocialInfo socialInfo) throws ValidationException {
        validateRegsiter(socialInfo);
        if (CheckUtils.isEmpty(socialInfo.getSocialType())) {
            throw new ValidationException(ErrorCodes.INCORRECT_DATA);
        }
    }

    public void validateRegsiter(RegisterInfo registerInfo) throws ValidationException {
        validateLogin(registerInfo);
        if (CheckUtils.isEmpty(registerInfo.getEmail())) {
            throw new ValidationException(ErrorCodes.INCORRECT_DATA);
        }
    }
}
