package main.java.com.lashgo.controller;

import org.jsondoc.core.pojo.JSONDoc;
import org.jsondoc.core.util.JSONDocUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Eugene on 12.05.2014.
 */
@Controller
@RequestMapping(value = "/")
public class DocumentationController {
    private String version;
    private String basePath;
    private List<String> packages;

    public void setVersion(String version) {
        this.version = version;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    JSONDoc getApi() {
        return JSONDocUtils.getApiDoc(version, basePath, packages);
    }
}
