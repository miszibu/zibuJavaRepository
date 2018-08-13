package com.zibu.demo.controller;

import com.zibu.demo.pojo.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    //创建线程安全的MAP
    static Map<Long, User> userMap = Collections.synchronizedMap(new HashMap<Long, User>());

    @RequestMapping(value = "/", method =RequestMethod.GET)
    public List<User> getUserList(){
        return new ArrayList<User>(userMap.values());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable Long id ){
        return userMap.get(id);
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String insertUser(@ModelAttribute User user){
        userMap.put(user.getId(),user);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public User deleteUser(@PathVariable Long userId){
        return  userMap.remove(userId);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public String modifyUser(@PathVariable Long id, User user){
        User previousUser = userMap.replace(id,user);
        if (previousUser==null){
            return  "fail";
        }else{
            return  "success";
        }
    }
}
