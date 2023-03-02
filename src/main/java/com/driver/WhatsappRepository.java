package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    private HashMap<String,User> user;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.user = new HashMap<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }


    public String createUser(String name, String mobile) throws Exception{


        User u = new User(name,mobile);
        try{
            if(user.containsKey(u.getMobile()))
            {
                throw new RuntimeException();
            }
            else{
                user.put(u.getMobile(),u);
            }
        }
        catch (Exception e)
        {
            System.out.println("User already exists");
        }

        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {


        Group group = new Group();
        if(users.size()==2)
        {
            group.setName(users.get(users.size()-1).getName());
            group.setNumberOfParticipants(users.size());
        }
         if(users.size()>2)
        {
            customGroupCount = customGroupCount+1;
            group.setName("Group"+" "+customGroupCount);
            group.setNumberOfParticipants(users.size());
        }
         adminMap.put(group,users.get(0));
         groupUserMap.put(group,users);

         return group;
    }


    public int createMessage(String content)
    {
        messageId = messageId +1;
        Message m = new Message();
        m.setId(messageId);
        m.setContent(content);

        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{

        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.

        boolean isPresent = false;

        try{
            if(!groupUserMap.containsKey(group))
            {
                throw new RuntimeException();
            }
        }
        catch(Exception e)
        {
            System.out.println("Group does not exist");
        }
        try{
            if(!userExistsInGroup(group,sender))
            {
                throw new RuntimeException();
            }
        }
        catch(Exception e)
        {
            System.out.println("You are not allowed to send message");
        }
        List<Message> messages = new ArrayList<>();
        if(groupMessageMap.containsKey(group)) messages = groupMessageMap.get(group);

        messages.add(message);
        groupMessageMap.put(group, messages);
        return messages.size();
    }
   public boolean userExistsInGroup(Group group,User sender)
   {
       List<User> users = groupUserMap.get(group);
       for(User user:users)
       {
           if(user.equals(sender))
           {
               return true;
           }
       }

       return false;
   }
    public String changeAdmin(User approver, User user, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.

        try{
            if(!adminMap.containsKey(group))
            {
                throw new RuntimeException();
            }
        }
        catch(Exception e){
            System.out.println("Group does not exist");
        }
        try{
            if(adminMap.containsKey(group))
            {
                if(adminMap.get(group).getName().equals(approver.getName())==false)
                {
                    throw new RuntimeException();
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Approver does not have rights");
        }

        try{
            if(groupUserMap.containsKey(group))
            {
                List<User> list = new ArrayList<>();
                for(User i:list)
                {
                    if(i.getName().equals(user))
                    {

                    }
                }
            }
            else {
                throw new RuntimeException();
            }
        }
        catch(Exception e)
        {
            System.out.println("User is not a participant");
        }

        if(adminMap.containsKey(group) && adminMap.get(group).getName().equals(approver.getName()))
        {
            if(groupUserMap.containsKey(group))
            {
                List<User> l = new ArrayList<>();
                for(User i:l)
                {
                    if(i.getName().equals(user.getName()))
                    {
                        adminMap.put(group,user);
                    }
                }
            }
        }


        return "SUCCESS";

    }

    public int removeUser(User user) throws Exception{
        return 0;
    }

    public String findMessage(Date start, Date end, int K) throws Exception{
        return "";
    }

    }

