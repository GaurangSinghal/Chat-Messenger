package com.example.ashish_pc.feedback;

/**
 * Created by ashish_pc on 4/9/2016.
 */
public class feedbackquestiontouser {
    private String question;
    private  int count;

    public feedbackquestiontouser(String question,int count)
    {
        super();
        this.question=question;
        this.count=count;

    }
    public String getquestion()
    {
        return question;
    }
    public int reviewcount()
    {
        return count;
    }



}
