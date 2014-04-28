package main.java.com.check.rest.controller;

import com.check.model.CheckApiHeaders;
import com.check.model.dto.Response;
import main.java.com.check.CheckConstants;
import main.java.com.check.service.PhotoService;
import main.java.com.check.service.SessionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by Eugene on 28.04.2014.
 */
@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private SessionValidator sessionValidator;

    @RequestMapping(value = "/photos/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getFile(@RequestHeader HttpHeaders httpHeaders, @PathVariable("fileName") String fileName) {
        sessionValidator.validate(httpHeaders);
        FileSystemResource resource = new FileSystemResource(new File(CheckConstants.PHOTOS_FOLDER, fileName));
        ResponseEntity<FileSystemResource> responseEntity = new ResponseEntity<>(resource, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/checks/{checkId}/photos", method = RequestMethod.POST)
    public
    @ResponseBody
    Response saveCheckPhoto(@RequestHeader HttpHeaders httpHeaders, @PathVariable("checkId") long checkId, @RequestParam("photo") MultipartFile file) {
        sessionValidator.validate(httpHeaders);
        photoService.savePhoto(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), checkId, file);
        return new Response();
    }

    @RequestMapping(value = "/photos/{photoId}/rate", method = RequestMethod.PUT)
    public
    @ResponseBody
    Response ratePhoto(@RequestHeader HttpHeaders httpHeaders, @PathVariable("photoId") long photoId) {
        sessionValidator.validate(httpHeaders);
        photoService.ratePhoto(httpHeaders.get(CheckApiHeaders.SESSION_ID).get(0), photoId);
        return new Response();
    }
}
