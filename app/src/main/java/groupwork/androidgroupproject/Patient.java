package groupwork.androidgroupproject;


public class Patient {
    private int patientID;
    private String patientDesc;
    private String patientReason;
    private String patientReasonTwo;
    private static String patientName;
    private String patientAddress;
    private double patientPhone;
    private String patientHealth;
    private String patientBirth;


    public Patient(int id){
        this.patientID= id;

    }


    Patient(int id, String name, String address, String birth, double phone, String health, String desc, String reason, String reasonTwo) {
        patientName=name;
        patientAddress=address;
        patientBirth=birth;
        patientPhone=phone;
        patientHealth= health;
        patientDesc=desc;
        patientReason=reason;
        patientReasonTwo=reasonTwo;
        patientID = id;

    }
    public static String getFName(){
        return patientName;
    }

    public String getName(){return patientName;}
    public String getAddress(){return patientAddress;}
    public String getBirth(){return patientBirth;}
    public double getPhone(){return patientPhone;}
    public String getHealth(){return patientHealth;}
    public String getDesc(){return patientDesc;}
    public String getReason(){return patientReason;}
    public String getReasonTwo(){return patientReasonTwo;};

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Patient) {
            Patient compare = (Patient) obj;
            return this.toString().equals(compare.toString());
        }
        return false;
    }
    public String toString(){
        return this.getFName() +" " + this.getAddress() +" " + this.getBirth() +" " + this.getPhone() +" " + this.getHealth() +" " + this.getDesc() +" " + this.getReason()
                +" "+ this.getReasonTwo();
    }
}