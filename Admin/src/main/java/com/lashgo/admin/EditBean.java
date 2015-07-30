package com.lashgo.admin;

import org.hibernate.Session;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Eugene on 21.06.2015.
 */
@ManagedBean(name = "edit")
@ViewScoped
public class EditBean {

    private  Integer id;
    private UploadedFile file;
    private Check check;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

   public void load(){
       if (id != null) {
           check = HibernateUtil.getSessionFactory().openSession().get(Check.class, id);
       } else {
           check = new Check();
       }
   }

    public String saveSuccess() {
        if (file != null) {
            check.setPhoto(file.getFileName());
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
            session.save(check);
        session.flush();
        session.close();
      return "checks.xhtml"     ;
    }


    public void fileUploaded(FileUploadEvent e) {
        file = e.getFile();
        try {
            savePhoto(file.getFileName(),file.getInputstream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void savePhoto(String photoName, InputStream inputStream) {
        if (inputStream != null) {
            BufferedImage src = null;
            try {
                src = ImageIO.read(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            StringBuilder photoDestinationBuilder = new StringBuilder(CheckConstants.PHOTOS_FOLDER);
            String photoPath = photoDestinationBuilder.append(photoName).toString();
            File destination = new File(photoPath);
            if (src != null) {
                try {
                    ImageIO.write(src, "jpg", destination);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
