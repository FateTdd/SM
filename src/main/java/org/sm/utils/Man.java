package org.sm.utils;

public class Man {
    private int  code;
    private boolean isFreedom=true;//Whether free
    private Woman partner;
    private Woman[] preferWoman;//Preference array
    private int sex=0;//0 for male and 1 for female.

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Man(int code){
        this.code=code;
    }

    public Woman[] getPreferWoman() {
        return preferWoman;
    }

    public void setPreferWoman(Woman[] preferWoman) {
        this.preferWoman = preferWoman;
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

    public Woman getPartner() {
        return partner;
    }

    public void setPartner(Woman partner) {
        this.partner = partner;
    }

    public String getName() {
        return "Man"+this.code;
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;//The address is equal
        }
        if(obj == null){
            return false;//Non-null: For any non-null reference x, x.quals (null) should return false.
        }
        if(obj instanceof Man){
            Man other = (Man) obj;
            //If the fields being compared are equal, the two objects are equal
            if(other.getCode()==this.getCode()){
                return true;
            }
        }
        return false;
    }

}
