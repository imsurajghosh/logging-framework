package org.example.log4s.models;

public enum LogLevel {
    ALL(0),
    TRACE(1),
    DEBUG(2),
    INFO(3),
    WARN(4),
    ERROR(5),


    ;

    private final int val;

    LogLevel(int val){
        this.val = val;
    }
}
