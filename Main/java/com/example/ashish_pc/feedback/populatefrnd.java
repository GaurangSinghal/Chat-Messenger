package com.example.ashish_pc.feedback;

/**
 * Created by ashish_pc on 9/20/2016.
 */
public class populatefrnd {
      String status;
      String name;
    String dob;
    String email;

    populatefrnd(String name,String status,String dob,String email)
    {
        this.name=name;
        this.status=status;
        this.dob=dob;
        this.email=email;
    }
    String getname()
    {
        return this.name;
    }
    String getdob()
    {
        return this.dob;
    }
    String getemail()
    {
        return this.email;
    }
    String getstatus()
    {
        return this.status;
    }
}
