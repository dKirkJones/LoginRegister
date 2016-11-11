package com.example.djones.theeshop;

/**
 * Created by djones on 10/5/16.
 */

public class Store {

    public String mNameOfStore;
    public String mAddress;
    public String mNameOfImage;
    public String mPopup;

    public Store (String startNameOfStore, String mAddress, String startNameOfImage, String startPopup){

        this.mNameOfStore = startNameOfStore;
        this.mAddress = mAddress;
        this.mNameOfImage = startNameOfImage;
        this.mPopup= startPopup;
    }

}
