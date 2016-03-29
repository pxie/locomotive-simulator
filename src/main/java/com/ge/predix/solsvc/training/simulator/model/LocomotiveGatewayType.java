package com.ge.predix.solsvc.training.simulator.model;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class LocomotiveGatewayType {

    @SerializedName("rpmData")
    @Expose
    private RPMData rpmData;
    
    @SerializedName("currentTime")
    @Expose
    private Long currentTime;
    
    @SerializedName("torquedata")
    @Expose
    private TorqueData torquedata;
    
    @SerializedName("locdata")
    @Expose
    private LocationData locdata;

    /**
     * 
     * @return
     *     The rpmData
     */
    public RPMData getRpmData() {
        return rpmData;
    }

    /**
     * 
     * @param rpmData
     *     The rpmData
     */
    public void setRpmData(RPMData rpmData) {
        this.rpmData = rpmData;
    }

    /**
     * 
     * @return
     *     The currentTime
     */
    public Long getCurrentTime() {
        return currentTime;
    }

    /**
     * 
     * @param currentTime
     *     The currentTime
     */
    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * 
     * @return
     *     The torquedata
     */
    public TorqueData getTorquedata() {
        return torquedata;
    }

    /**
     * 
     * @param torquedata
     *     The torquedata
     */
    public void setTorquedata(TorqueData torquedata) {
        this.torquedata = torquedata;
    }

    /**
     * 
     * @return
     *     The locdata
     */
    public LocationData getLocdata() {
        return locdata;
    }

    /**
     * 
     * @param locdata
     *     The locdata
     */
    public void setLocdata(LocationData locdata) {
        this.locdata = locdata;
    }


}
