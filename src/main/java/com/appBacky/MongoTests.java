package com.appBacky;

import com.appBacky.model.Comment;
import com.appBacky.model.JSONConverter;
import com.appBacky.model.Post;
import com.appBacky.model.Reply;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;

import com.mongodb.client.MongoClients;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Path("mongo")
public class MongoTests {
    //Create a new instance of mongoClient for backend to use
    //CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
    //CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),pojoCodecRegistry);
    //ConnectionString connectionString = new ConnectionString(System.getProperty("mongodb://localhost:27017"));
    //MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).codecRegistry(codecRegistry).build();
    //MongoClient mongoClient = MongoClients.create(clientSettings);
    //mongoClient.
    //CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
    //        fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    //MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());


    //----------------------------------------
    //General
    //----------------------------------------
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(){

        ConnectToDB connect = new ConnectToDB();
        connect.demo(mongoClient);

        //TestClass testClass = new TestClass();
        //testClass.demo2();


        return "Mongo Tested";
    }


    //----------------------------------------
    //POST
    //----------------------------------------

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String addPost(Post post){
    //validate post

    // check if post doesnt have currentid - return with note if failed
    //Convert post to json
    // upload to mongod



    return "WIP";
    }

    @Path("post")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPost(@QueryParam("Id") Integer Id){
        Post post = new Post();
        Document doc = post.getPost(Id, mongoClient);
        JSONConverter converter = new JSONConverter();
        String json = converter.convertDoctoJson(doc);
        //using doc because i cant get post convertion working
        //IT WORKS DONT TOUCH THIS!

        return json;
    }
    //getPostList
    @Path("getPostList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPostList(@QueryParam("Id") Integer Id){
        Post post = new Post();
        List<Document> docs = post.getPostList(Id, mongoClient);
        JSONConverter converter = new JSONConverter();
        String json = "";
        for(Document d : docs){
            json = json + converter.convertDoctoJson(d);
        }
        //using doc because i cant get post convertion working
        //IT WORKS DONT TOUCH THIS!

        return json;
    }




    //deletePost
    @Path("deletePost")
    @DELETE
    public String deletePost(@QueryParam("Id") Integer Id){
        Post post = new Post();
        String response = post.deletePost(Id,mongoClient );
        return  response;
    }


    @Path("dbg")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Post getDBGPost(){
        Post post = new Post();
        post.debugPost();


        return post;
    }





    //--------------------------------------------------------
    //Comment stuff
    //----------------------------------------------------------------------------------------------------------------------------------------------------------

    //deleteComment
    @Path("deleteComment")
    @DELETE
    public String deleteComment(@QueryParam("Id") Integer Id){
        Comment comment = new Comment();
        String response = comment.deleteComment(Id,mongoClient );
        return  response;
    }

    @Path("comment")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getComment(@QueryParam("Id") Integer Id){
        Comment comment = new Comment();
        Document doc = comment.getComment(Id, mongoClient);
        JSONConverter converter = new JSONConverter();
        String json = converter.convertDoctoJson(doc);
        //using doc because i cant get post convertion working
        //IT WORKS DONT TOUCH THIS!

        return json;
    }








    //--------------------------------------------------------
    //Reply stuff
    //----------------------------------------------------------------------------------------------------------------------------------------------------------


    //deleteReply
    @Path("deleteReply")
    @DELETE
    public String deleteReply(@QueryParam("Id") Integer Id){
        Reply reply = new Reply();
        String response = reply.deleteReply(Id,mongoClient );
        return  response;
    }


    @Path("reactToReply")
    @POST
    public String reactToReply(@QueryParam("Id") Integer Id, @QueryParam("reaction") String reactionId){
        Reply reply = new Reply();

        String response = reply.reactToReply(Id,reactionId,mongoClient);
        return response;

    }
    @Path("reply")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getReply(@QueryParam("Id") Integer Id){
        Reply reply = new Reply();
        Document doc = reply.getReply(Id, mongoClient);
        JSONConverter converter = new JSONConverter();
        String json = converter.convertDoctoJson(doc);
        //using doc because i cant get post convertion working
        //IT WORKS DONT TOUCH THIS!

        return json;
    }







}


//public class ConnectToDB {
//    public static void main( String args[] )