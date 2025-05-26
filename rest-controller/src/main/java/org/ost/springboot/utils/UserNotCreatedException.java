package org.ost.springboot.utils;

public class UserNotCreatedException extends RuntimeException{

    public UserNotCreatedException(String msg) {
        super(msg);
    }

}
