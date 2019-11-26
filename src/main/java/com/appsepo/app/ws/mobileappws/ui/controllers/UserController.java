package com.appsepo.app.ws.mobileappws.ui.controllers;

import com.appsepo.app.ws.mobileappws.ui.model.request.UpdateUserDetailsRequestModel;
import com.appsepo.app.ws.mobileappws.ui.model.request.UserDetailsRequestModel;
import com.appsepo.app.ws.mobileappws.ui.model.response.UserRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    Map<String, UserRest> users;

    @GetMapping(path = "/{userId}",
    produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {

        if(users.containsKey(userId)){
            return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    public ResponseEntity<UserRest> createUser(@Valid @RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();
        returnValue.setEmail(userDetails.getEmail());
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());

        String userId = UUID.randomUUID().toString();
        if(users == null){
            users = new HashMap<>();
        }
        returnValue.setUserId(userId);
        users.put(userId, returnValue);
        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @PutMapping(
            path = "/{userId}",
            consumes = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            },
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    public UserRest updateUser(@PathVariable String userId,
                             @Valid @RequestBody UpdateUserDetailsRequestModel updateUserDetails){
        UserRest storedUser = users.get(userId);
        storedUser.setFirstName(updateUserDetails.getFirstName());
        storedUser.setLastName(updateUserDetails.getLastName());
        users.put(userId, storedUser);
        return storedUser;
    }

    @DeleteMapping( path = "/{id}"
    )
    public ResponseEntity deleteUser(@PathVariable String id){
        users.remove(id);
        return ResponseEntity.noContent().build();
    }
}
