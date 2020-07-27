package org.sm.utils;

public class Woman {
    private boolean isFreedom=true;//Whether free
    private Man[] preferMan;//Preference array
    private Man partner;
    private int  code;
//    private int sex=1;//0 for male and 1 for female.
//
//    public int getSex() {
//        return sex;
//    }
//
//    public void setSex(int sex) {
//        this.sex = sex;
//    }

    public Woman(int code){
        this.code=code;
    }


    public Man[] getPreferMan() {
        return preferMan;
    }

    public void setPreferMan(Man[] preferMan) {
        this.preferMan = preferMan;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public boolean isFreedom() {
        return isFreedom;
    }

    public void setFreedom(boolean freedom) {
        isFreedom = freedom;
    }

    public Man getPartner() {
        return partner;
    }

    public void setPartner(Man partner) {
        this.partner = partner;
    }

    public String getName() {
        return "Woman"+this.code;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;//The address is equal
        }
        if(obj == null){
            return false;//Non-null: For any non-null reference x, x.quals (null) should return false.
        }
        if(obj instanceof Woman){
            Woman other = (Woman) obj;
            //If the fields being compared are equal, the two objects are equal
            if(other.getCode()==this.getCode()){
                return true;
            }
        }
        return false;
    }


}