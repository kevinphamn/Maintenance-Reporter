package com.example.cse110.map;
import com.parse.Parse;

/**
 * Created by Justin on 11/22/2015.
 */
public class Map extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "ZAe2fMrl1qDlJe4EExrtimZwPWJ5G9HvURz6jprR", "epI02Me6dny9AXtCt0bKY91onpphrkiIHJPTpx2r");
    }
}
