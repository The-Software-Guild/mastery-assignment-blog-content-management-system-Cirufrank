/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.we.blogcms.dao;

import com.we.blogcms.model.Status;
import org.springframework.stereotype.Component;

/**
 *
 * @author ciruf
 */
@Component
public class DaoHelper {
    final static String DELIMITER = ",", 
            SINGLE_QUOTE = "'";
    
    public String createInStatusText(Status[] statuses) {
        final int ONE_STATUS = 1;
        String inString = "(";
        for (int index = 0; index < statuses.length; index += 1) {
            final int LAST_INDEX = statuses.length - 1;
            final Status currentStatus = statuses[index];
            if (index == LAST_INDEX || statuses.length == ONE_STATUS) {
                inString += SINGLE_QUOTE + currentStatus.toString()
                        + SINGLE_QUOTE + ")";
                break;
            }
            inString += SINGLE_QUOTE + currentStatus.toString()
                        + SINGLE_QUOTE + DELIMITER;
            
        }
        return inString;
    }
}
