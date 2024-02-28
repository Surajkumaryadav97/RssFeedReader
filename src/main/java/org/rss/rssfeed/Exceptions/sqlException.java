package org.rss.rssfeed.Exceptions;

import java.sql.SQLException;

public class sqlException extends Exception{
    public sqlException(String message ,Throwable cause){
        super(message,cause);
    }
}
