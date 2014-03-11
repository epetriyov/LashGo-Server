package main.java.com.check.web;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 10.03.14
 * Time: 13:25
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "checkcreator")
@SessionScoped
public class CheckCreator {

    @Resource(lookup = "check_resource")
    DataSource dataSource;
    private String checkname;
    private String description;
    private String duration;
    private UploadedFile uploadedFile;

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String createPerson() {
        try {
            InputStream stream = uploadedFile.getInputstream();
            String prefix = FilenameUtils.getBaseName(uploadedFile.getFileName());
            String suffix = FilenameUtils.getExtension(uploadedFile.getFileName());
            File file = File.createTempFile(prefix + "-", "." + suffix, new File("D:/Check/photos"));
            OutputStream output = new FileOutputStream(file);
            try {
                IOUtils.copy(stream, output);
            } finally {
                IOUtils.closeQuietly(output);
                IOUtils.closeQuietly(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int resultSet;
        Connection con = null;
        try {
            con = dataSource.getConnection();
            if (con == null)
                throw new SQLException("Can't get database connection");
            PreparedStatement ps
                    = con.prepareStatement(
                    "insert into checks (name,info,duration,image) values (?,?,?,?)");
            ps.setString(1, checkname);
            ps.setString(2, description);
            ps.setInt(3, duration != null ? Integer.valueOf(duration) : 0);
            ps.setString(4, uploadedFile.getFileName());
            resultSet = ps.executeUpdate();
            if (resultSet > 0) {
                return "addcheck";
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getCheckname() {
        return checkname;
    }

    public void setCheckname(String checkname) {
        this.checkname = checkname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
