package com.appBacky.model;

import com.mongodb.MongoClient;

import java.util.Date;

public class Reaction {

    public Date Created;

    public Integer Reaction;

    public Integer ReactedBy;


    Reaction(Date date,Integer reactionType,Integer Id){ //all three constructor
        this.Created = date;
        this.Reaction = reactionType;
        this.ReactedBy = Id;
    }

    public Reaction(Integer reactionType, Integer Id){ //Dateless constructor
        this.Created = new Date();
        this.Reaction = reactionType;
        this.ReactedBy = Id;
    }

    Reaction(){ //Generic constructor

    }




}
