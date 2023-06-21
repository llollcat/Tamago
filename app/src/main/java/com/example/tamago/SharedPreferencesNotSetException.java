package com.example.tamago;

public class SharedPreferencesNotSetException extends Exception {
    public SharedPreferencesNotSetException(){
        super("class have not SharedPreferences object to wort with it. You have to set as argument");
    }
}
