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
    "array":[
        "el1",
        "el2"
    ],
    "bool":true
}*/




//

        String json = """
            {
                "id": "3b049946-ed7e-40ba-a7cb-f3585409da22",
                "firstName": "Reuben",
                "lastName": "Martin",
                "dateBirth": "2003-11-03",
                "orders": [
                    {
                        "id": "956bb29b-8191-4de5-9e8e-8df759525831",
                        "products": [
                            {
                              "id": "50faf7eb-6792-45a7-a3cd-91bb63de48f6",
                              "name": "Теlеfон",
                              "price": 100.0
                            },
                            {
                              "id": "6b3a9d70-43e0-4c87-b72d-45fe79ee41c4",
                              "name": "Машина",
                              "price": 100.0
                            }
                        ],
                        "createDate": "2023-10-24T17:50:30.5470749+03:00"
                    }
                ]
            }
        """;

        JsonParser jsonParser = new JsonParser();
        jsonParser.parse(json, Person.class);

        System.out.println();

    }
}