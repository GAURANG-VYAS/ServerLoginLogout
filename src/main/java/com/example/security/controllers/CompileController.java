package com.example.security.controllers;
import com.example.security.entities.AppUserEntity;
import com.example.security.entities.PostEntity;
import com.example.security.models.Code;
import com.example.security.repositories.AppUserRepository;
import com.example.security.repositories.ProblemRepository;
import com.example.security.repositories.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.security.entities.ProblemEntity;
import com.example.security.config.AppConfig;
import org.springframework.data.mongodb.core.MongoOperations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import org.json.*;

public class CompileController {
    @RequestMapping(value= "/compile", method = RequestMethod.POST)

    public @ResponseBody String compileCode(@RequestBody Code jsonString)
    {
        String token = "144e1b784f339ff7538614188efcca31";
        String testUrl = "http://6159f1ee.compilers.sphere-engine.com/api/v3/test?access_token=" + token;
        String submitDetUrl = "http://6159f1ee.compilers.sphere-engine.com/api/v3/submissions/:id?access_token=" + token;
        String compilerUrl = "http://6159f1ee.compilers.sphere-engine.com/api/v3/compilers?access_token=" + token;
        String postUrl = "http://6159f1ee.compilers.sphere-engine.com/api/v3/submissions?access_token=" + token;


        System.out.println("Received Request");
        try {
            int responseCode;
            int submissionId= 62706435; // Use this id to save number of compilations.
            //int submissionId = 62599488;


     //Comment till return statement if necessary

      // Establishing a connection
      URL obj = new URL(postUrl);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "application/json");
      con.setDoOutput(true);

      // Making Json Data and sending code
      JSONObject codeData = new JSONObject();
      codeData.put("language", String.valueOf(jsonString.getId()));
      codeData.put("sourceCode", jsonString.getCode());
      String finalCodeData = codeData.toString();
      System.out.println("Final JSON Object : " + finalCodeData);


      OutputStreamWriter jsonDataWriter = new OutputStreamWriter(con.getOutputStream());
      jsonDataWriter.write(finalCodeData);
      jsonDataWriter.flush();

      System.out.println("Wrote Data");
      responseCode = con.getResponseCode();
      System.out.println("Response Code : " + responseCode);
      // Getting Submission Id and Checking Status
      if (responseCode == 201) {
        System.out.println("Response Code Ok ");
        BufferedReader in = new BufferedReader(new InputStreamReader(
          con.getInputStream()));

        String inputLine;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        JSONTokener jsonData = new JSONTokener(new String(response));
        JSONObject root = new JSONObject(jsonData);
        submissionId = root.getInt("id");

        System.out.println("Your Submission Id : " + submissionId);
        in.close();
        //Checking Status

        // print result
        //System.out.println(response.toString());
      }
      return "{\"subId\" : "+submissionId+"}";

        }
        catch(Exception e) {
            return "{\"res\" :\"Error\"}";
        }
        //return "{\"res\" :\"Error\"}";
    }


    @RequestMapping(value= "/status/{subId}", method = RequestMethod.GET)
    public String getSubmissionStatus(@PathVariable(value="id") String subId) throws Exception
    {
        //Thread.sleep(2000);
        System.out.println("Get Submission Status ");
        String token  = "144e1b784f339ff7538614188efcca31";
        String testUrl  = "http://6159f1ee.compilers.sphere-engine.com/api/v3/test?access_token="+token;
        String submitDetUrl = "http://6159f1ee.compilers.sphere-engine.com/api/v3/submissions/"+subId+"?access_token="+token+
                "&withOutput=true&withStderr=true&withCmpinfo=true";

        System.out.println("Your Url : "+submitDetUrl);

        int responseCode;

        URL obj = new URL(submitDetUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        responseCode = con.getResponseCode();

        System.out.println("Submission Status Response Code : "+responseCode);
        if (responseCode == 200)
        {
            System.out.println("Response Code Ok ");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JSONTokener jsonData = new JSONTokener(new String(response));
            JSONObject root = new JSONObject(jsonData);
            System.out.println("Your Submission Response : "+root.toString());
            return root.toString();

        }
        return "{\"res\" :\"Error\"}";
    }
}
