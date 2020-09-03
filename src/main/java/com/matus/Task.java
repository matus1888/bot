package com.matus;

import java.util.Date;
import java.util.TimerTask;

public class Task extends TimerTask {
    Date now;
    public void run() {
        // Write code here that you want to execute periodically.
        now = new Date();                      // initialize date
        System.out.println("Time is :" + now); // Display current time
    }
}