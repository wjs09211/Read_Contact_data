package hw.lab3.s1021527;

/**
 * Created by 睡睡 on 2015/11/21.
 */
public class ContactItem {
    private String ID = "";
    private String name = "";
    private String phoneNumber = "";
    private String email = "";
    private String address ="";
    private int times = 0;
    private int duration = 0;
    public ContactItem(){}

    public String getID(){
        return ID;
    }
    public String getEmail() {
        return email;
    }
    public String getName(){
        return name;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getAddress(){
        return  address;
    }
    public int getTimes(){
        return times;
    }
    public int getDuration(){
        return duration;
    }

    public void setID(String str){
        ID = str;
    }
    public void setName(String str){
        name = str;
    }
    public void setEmail(String str){
        email = str;
    }
    public void setPhoneNumber(String str){
        phoneNumber = str;
    }
    public void setAddress(String str){
        address = str;
    }
    public void addTimes(){
        times++;
    }
    public void addduration(int a){
        duration += a;
    }
}