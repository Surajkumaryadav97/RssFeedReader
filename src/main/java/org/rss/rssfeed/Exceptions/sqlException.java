package org.rss.rssfeed.Exceptions;

import java.sql.SQLException;

public class sqlException extends SQLException{
    public sqlException(String message ,Throwable cause){
        super(message,cause);
    }
}
