package com.lashgo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by Eugene on 03.09.2014.
 */
public class ImportChecks {

    private static final String CHECKS_FILE_NAME = "D:\\LashGo\\projects\\LashGo\\Server\\LashGoUtils\\checks_new.xml";

    private static List<Check> parseChecks() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z"));
        XMLInputFactory f = XMLInputFactory.newFactory();
        try {
            XMLStreamReader xmlStreamReader = f.createXMLStreamReader(new FileInputStream(new File(CHECKS_FILE_NAME)));
            try {
                return xmlMapper.readValue(xmlStreamReader, new TypeReference<List<Check>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    private static void insertChecks(final List<Check> checkList) {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setDatabaseName("lashgo");
        pgSimpleDataSource.setPassword("Javageeks58");
        pgSimpleDataSource.setPortNumber(5432);
        pgSimpleDataSource.setServerName("78.47.39.245");
        pgSimpleDataSource.setUser("postgres");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(pgSimpleDataSource);
        try {
            jdbcTemplate.batchUpdate("INSERT INTO checks (name,description,duration,vote_duration,start_date,task_photo) " +
                            "VALUES (?,?,?,?,?,?)",
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            Check check = checkList.get(i);
                            ps.setString(1, check.getName());
                            ps.setString(2, check.getDescription());
                            ps.setInt(3, check.getDuration());
                            ps.setInt(4, check.getVoteDuration());
                            ps.setTimestamp(5, new Timestamp(check.getStartDate().getTime()));
                            ps.setString(6, check.getPhoto());
                        }

                        @Override
                        public int getBatchSize() {
                            return checkList.size();
                        }
                    });
        } catch (DataAccessException e) {
            System.err.print(e.getMessage());
        }
    }

    public static void main(String[] args) {
        insertChecks(parseChecks());
    }
}
