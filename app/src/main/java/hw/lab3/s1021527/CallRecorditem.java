package hw.lab3.s1021527;

/**
 * Created by 睡睡 on 2015/11/25.
 */
public class CallRecorditem {
    private String phoneNumber = "";
    String type = "";
    String date = "";
    String duration = "";
    public CallRecorditem(){}
    public String getPhoneNum(){
        return phoneNumber;
    }
    public void setPhoneNum(String str){
        phoneNumber = str;
    }
    public String getType(){
        return type;
    }
    public void setType(String str){
        type= str;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String str){
        date= str;
    }
    public String getDuration(){
        return duration;
    }
    public void setDuration(String str){
        duration= str;
    }
}
