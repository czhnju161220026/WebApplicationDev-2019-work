package cn.edu.nju.web.util;

public class User {
    private int uid;
    private String userName;
    private String mail;
    private String pwd;
    private int isActivated;
    private String activateCode;

    public User(int uid, String userName, String mail, String pwd, String activateCode) {
        this.uid = uid;
        this.userName = userName;
        this.mail = mail;
        this.pwd = pwd;
        this.isActivated = 0;
        this.activateCode = activateCode;
    }

    public int getUid() { return uid; }

    public String getUserName() { return userName; }

    public String getMail() { return mail; }

    public String getPwd() { return pwd; }

    public int getIsActivated() { return isActivated; }

    public String getActivateCode() { return activateCode; }

    public void setUid(int uid) { this.uid = uid; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setMail(String mail) { this.mail = mail; }

    public void setPwd(String pwd) { this.pwd = pwd; }



}
