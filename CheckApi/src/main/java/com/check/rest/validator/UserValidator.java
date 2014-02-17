package main.java.com.check.rest.validator;

import com.check.model.dto.LoginInfo;
import main.java.com.check.rest.error.ErrorCodes;
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

    public static void validateLogin(LoginInfo loginInfo, List<String> uuidHeaders) throws Exception {
        if (CollectionUtils.isEmpty(uuidHeaders)) {
            throw new Exception(ErrorCodes.UUID_IS_EMPTY);
        }
        if (loginInfo == null) {
            throw new Exception(ErrorCodes.INCORRECT_DATA);
        }

    }


}
