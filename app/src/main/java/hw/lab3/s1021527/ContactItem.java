package hw.lab3.s1021527;

/**
 * Created by 睡睡 on 2015/11/21.
 */
public class ContactItem {
    private String ID = "";
    private String name = "";
    private String phoneNumber = "";
    private String email = "";
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
}