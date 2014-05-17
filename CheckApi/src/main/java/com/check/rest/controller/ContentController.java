package main.java.com.check.rest.controller;

import com.check.model.CheckApiHeaders;
import com.check.model.dto.CheckDtoList;
import com.check.model.dto.ContentDto;
import com.check.model.dto.ContentDtoList;
import com.check.model.dto.Response;
import main.java.com.check.service.ContentService;
import main.java.com.check.service.SessionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Eugene on 06.05.2014.
 */
@Controller
public class ContentController extends BaseController {

    @Autowired
    private ContentService contentService;

    @Autowired
    private SessionValidator sessionValidator;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public
    @ResponseBody
    Response<ContentDtoList> getNews(@RequestHeader HttpHeaders httpHeaders) {
        sessionValidator.validate(httpHeaders);
        return new Response<>(contentService.getNews(httpHeaders.get(CheckApiHeaders.CLIENT_TYPE).get(0)));
    }
}
