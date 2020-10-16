package com.appBacky;

import com.appBacky.model.*;
import com.mongodb.MongoClient;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;


public class ClientFacing {

    MongoClient mongoClient = new MongoClient( "localhost" , 27017 );


    //----------------------------------------
    //POST
    //----------------------------------------
    @Path("/posts")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String addPost(Post post){
    //validate post

    // check if post doesnt have currentid - return with note if failed
    //Convert post to json
    // upload to mongod
    return "WIP";
    }

    @Path("/posts/{postId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPost(@QueryParam("postId") Integer Id){
        Post post = new Post();
        Document doc = post.getPost(Id, mongoClient);
        JSONConverter converter = new JSONConverter();
        String json = converter.convertDoctoJson(doc);
        //using doc because i cant get post convertion working
        //IT WORKS DONT TOUCH THIS!

        return json;
    }
    //posts
    @Path("/posts")
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
        return json;
    }




    //deletePost
    @Path("posts/{postId}")
    @DELETE
    public String deletePost(@QueryParam("postId") Integer Id){
        Post post = new Post();
        String response = post.deletePost(Id,mongoClient );
        return  response;
    }

    @Path("/posts/{postId}/reactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String addReaction(@QueryParam("postId") Integer Id){
        Post post = new Post();
        Document doc = post.getPost(Id, mongoClient);
        JSONConverter converter = new JSONConverter();
        String json = converter.convertDoctoJson(doc);
        //using doc because i cant get post convertion working
        //IT WORKS DONT TOUCH THIS!

        return json;
    }



    //--------------------------------------------------------
    //Comment stuff
    //----------------------------------------------------------------------------------------------------------------------------------------------------------
    @Path("/posts/{postId}/comments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllComment(@QueryParam("Id") Integer postId){
        Comment comment = new Comment();
        Document doc = comment.getComment(postId, mongoClient);
        JSONConverter converter = new JSONConverter();
        String json = converter.convertDoctoJson(doc);

        return json;
    }

    @Path("/posts/{postId}/comments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String addComment(@QueryParam("Id") Integer postId){
        Comment comment = new Comment();
        String text ="";

        comment.addComment(postId,text,1, mongoClient);
        return "ok";
    }

    @Path("/posts/{postId}/comments/{commentId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String addNewComment(@QueryParam("postId") Integer postId, @QueryParam("commentId") Integer commentId, String newText){
        Comment comment = new Comment();
        String result = comment.updateComment(commentId, newText, mongoClient);

        return result;
    }


    @Path("/posts/{postId}/comments/{commentId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteComment(@QueryParam("postId") Integer postId, @QueryParam("commentId") Integer commentId){
        Comment comment = new Comment();
        String result = comment.updateComment(commentId, "", mongoClient);

        return result;
    }


    @Path("/posts/{postId}/comments/{commentId}/replies")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reply> getAllCommentReply(@QueryParam("postId") Integer postId, @QueryParam("commentId") Integer commentId){
        Comment comment = new Comment();
        List<Reply> doc = comment.getCommentReplies(commentId, mongoClient);
        JSONConverter converter = new JSONConverter();


        return doc;
    }


    @Path("/posts/{postId}/comments/{commentId}/replies")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String addReply(@QueryParam("postId") Integer postId, @QueryParam("commentId") Integer commentId ){
        Reply reply = new Reply();
        reply.Text = "";
        reply.Created = new Date();
        reply.Author = 0;
        Comment comment = new Comment();
        String status = comment.addReply(commentId, mongoClient, reply);
        JSONConverter converter = new JSONConverter();


        return status;
    }






    //deleteComment
    @Path("deleteComment")
    @DELETE
    public String deleteComment(@QueryParam("Id") Integer Id){
        Comment comment = new Comment();
        String response = comment.deleteComment(Id,mongoClient );
        return  response;
    }










    //--------------------------------------------------------
    //Reply stuff
    //----------------------------------------------------------------------------------------------------------------------------------------------------------


    @Path("/posts/{postId}/comments/{commentId}/replies/{replyId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String updateReplyText(@QueryParam("postId") Integer postId, @QueryParam("commentId") Integer commentId, @QueryParam("replyId") Integer replyId ){
        String text = "";
        Reply reply = new Reply();
        String status = reply.updateReplyText( mongoClient, replyId, text);
        JSONConverter converter = new JSONConverter();


        return status;
    }

    @Path("/posts/{postId}/comments/{commentId}/replies/{replyId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String addReplyReaction(@QueryParam("postId") Integer postId, @QueryParam("commentId") Integer commentId, @QueryParam("replyId") Integer replyId ){
        Integer reactionId = 0;
        Integer user = 0;
        Reply reply = new Reply();
        Reaction reaction = new Reaction(reactionId, user);
        String status = reply.addReplyReaction( mongoClient, replyId, reaction);
        JSONConverter converter = new JSONConverter();


        return status;
    }

    //deleteReply
    @Path("/posts/{postId}/comments/{commentId}/replies/{replyId}")
    @DELETE
    public String deleteReply(@QueryParam("postId") Integer postId, @QueryParam("commentId") Integer commentId, @QueryParam("replyId") Integer replyId ){
        Reply reply = new Reply();
        String response = reply.deleteReply(replyId,mongoClient );
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