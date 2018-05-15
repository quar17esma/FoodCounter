package com.quar17esma.exceptions;

public class BusyEmailException extends RuntimeException {

    private String name;

    private String email;

    public BusyEmailException(String message, String name, String email) {
        super(message);
        this.name = name;
        this.email = email;
    }

    public BusyEmailException(BusyEmailException e) {
         super(e.getMessage());
         this.name = e.name;
         this.email = e.email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
