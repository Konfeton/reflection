package com.onkonfeton;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
/*
* {
  "first_name" : "Sammy",
  "last_name" : "Shark",
  "location" : "Ocean",
  "null" : null,
  "websites" : [
    {
      "description" : "work",
      "URL" : "https://www.digitalocean.com/"
    },
    {
      "desciption" : "tutorials",
      "URL" : "https://www.digitalocean.com/community/tutorials"
    }
  ],
  "social_media" : [
    {
      "description" : "twitter",
      "link" : "https://twitter.com/digitalocean"
    },
    {
      "description" : "facebook",
      "link" : "https://www.facebook.com/DigitalOceanCloudHosting"
    },
    {
      "description" : "github",
      "link" : "https://github.com/digitalocean"
    }
  ],
  * "array":[
  *     "el1",
  *     "el2"
  * ],
  * "bool":true
}*/

        String json = """
            {   "name":"John", "age":30, "isStudent":false, "courses":[ "Math", "Science"]}
    
        """;

        JsonParser jsonParser = new JsonParser();
        jsonParser.parse(json, Person.class);

        System.out.println();

    }
}