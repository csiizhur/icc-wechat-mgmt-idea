package com.iccspace.controller.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iccspace.config.CustomTimestampDeserialize;
import com.iccspace.config.CustomTimestampSerializer;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/21.
 */
public class ShopsEditModel implements Serializable{

    private static final long serialVersionUID = 2243503216237243781L;
    private String shopsId;
    private String estatesType;
    private String floor;
    private BigDecimal rentFee;
    private String mobilePhone;
    //@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat()
    private Timestamp releaseDate;
    //private Date releaseDate;
    private double shopSize;
    private String shopsAddress;

    public String getEstatesType() {
        return estatesType;
    }

    public void setEstatesType(String estatesType) {
        this.estatesType = estatesType;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public BigDecimal getRentFee() {
        return rentFee;
    }

    public void setRentFee(BigDecimal rentFee) {
        this.rentFee = rentFee;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    //@JsonSerialize(using = DateJsonSerializer.class)
    @JsonSerialize(using = CustomTimestampSerializer.class)
    public Timestamp getReleaseDate() {
        return releaseDate;
    }
    @JsonDeserialize(using = CustomTimestampDeserialize.class)
    //@JsonDeserialize(using = DateJsonDeserializer.class)
    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getShopSize() {
        return shopSize;
    }

    public void setShopSize(double shopSize) {
        this.shopSize = shopSize;
    }

    public String getShopsAddress() {
        return shopsAddress;
    }

    public void setShopsAddress(String shopsAddress) {
        this.shopsAddress = shopsAddress;
    }

    public String getShopsId() {
        return shopsId;
    }

    public void setShopsId(String shopsId) {
        this.shopsId = shopsId;
    }

    public static void main(String args[]){
        String a = "{\"shopsId\":\"myName\",\"releaseDate\":\"2014-11-11 19:01:58\"}";
        //String a1 = "{shopsId='zhangsan',releaseDate=1412511615062}";
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            ShopsEditModel shopsEditModel = objectMapper.readValue(a,ShopsEditModel.class);
            System.err.print(shopsEditModel);

            /*ShopsEditModel s = new ShopsEditModel();
            s.setReleaseDate(new Date());
            s.setShopsId("qwe");
            String ss = objectMapper.writeValueAsString(s);
            System.err.print(ss);*/
        }catch(IOException e){
            System.err.print(e);
        }
    }
}
