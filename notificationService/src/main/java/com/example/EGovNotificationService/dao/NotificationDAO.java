package com.example.EGovNotificationService.dao;

import com.example.EGovNotificationService.data.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class NotificationDAO {
    Logger logger = LoggerFactory.getLogger(NotificationDAO.class);
    final String sqlInsertNotificationData = "";
    private DataSource dataSource;

    //Datasource bean is injected
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertData(Event event, String message) {
        logger.info("## Insert Notification Data");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        // todo : write insertion call
    }

}