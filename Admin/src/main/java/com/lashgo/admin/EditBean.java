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

    private UploadedFile file;
    private Check check;

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

    @PostConstruct
    public void test() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String id = facesContext.getExternalContext().
                getRequestParameterMap().get("id");
        if (id != null) {
            check = HibernateUtil.getSessionFactory().openSession().get(Check.class, Integer.valueOf(id));
        } else {
            check = new Check();
        }
    }

    public void saveSuccess() {
        if (file != null) {
            check.setPhoto(file.getFileName());
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (check.getId() > 0) {
            session.update(check);
        } else {
            session.save(check);
        }
        session.flush();
        session.close();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("checks.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAction(ActionEvent event) {
        saveSuccess();
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
