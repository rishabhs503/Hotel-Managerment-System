# Hotel-Feedback-Rating-System

## Requirements

- MongoDB, IDE, Postman or Web Browser & Codes

## Installation

- All the `code` required to get started
- Install `MongoDB` to local system. Thats it.
  
- Use the link to Download the Mongo Community Server i.e MongoDB: 

  https://www.mongodb.com/download-center/community?jmp=docs
  			or Direct Download
  https://fastdl.mongodb.org/win32/mongodb-win32-x86_64-2012plus-4.2.2-signed.msi

After installation, Open cmd/powershell and start Mongo Server. 
- Type Command-> `mongo`	
Minimize the cmd window. 
  	
### Clone

- Clone this repo to your local machine using `git-link`

### Setup & Run

- This Microservice have Feedback & Rating functionality and tasks.
- Open the Microservice in any IDE i.e STS or Intellij etc.
- Make sure Mongo is running in backround. You dont need to configure the Database sepearately.
- Now goto `hotel` package of each microservice, and run the Java Application as Spring Boot.
 ` Ex. FeedBackServiceApplication.java -> Right Click -> Run as -> Spring Boot App `
- Run all the 4 microservices similarly. It will run on seperate Ports. Done!
- Now lets make some BOOKINGSSS!

> API Endpoints & Ports:

- Common API Endpoints : /create, /update, /delete, /fetchAll
- Port Numbers : 8009

> Sample Commands for quick Testing:

- Use Postman or Chrome

- Hotel-Feedback Details Microservice
1) http://localhost:8009/feedback/create (Post)

{
    "message": "Tv not working during 2 days stay at hotel. After changing room and many complaints hotel management was unable to resolve the issue.",
    "title": "Amenities not completely managed",
    "stayType": String
    "filters": ["location", "connectivity", "market", "central location", "patio", "service quality", "staff courtery", "room cleanliness", "place cleanliness", "hotel cleanliness", "location cleanliness", "food", "breakfast", "resturent"],
    "userId": 1006,
    "hotelId": 101,
    "ratings": {
        "location": 4,
        "value for money": 4,
        "facilities": 3,
        "hospitality": 3,
        "cleanliness": 4
    }
}
   
2) http://localhost:8080/feedback/update (Put)

{
    "message": "Bad Review",
    "title": "Happy update",
    "filters": ["market", "central location"]
}


3) http://localhost:8080/feedback/delete/{feedbackId}  (Delete)
4) http://localhost:8080/feedback/fetchAll/{hotelId} (Get)

- Hotel-Rating Manager Microservice


## Postman Screenshot

- Quickly view all the API Request and responses. (Screenshots)
- Link: `https://drive.google.com`

Project Requirement & Deliverables:

Stack   Requirements 
 
- All   requests   should   be   handled   by   RESTful   API   (use   any   framework   you   like) 
- The   frontend   should   be   implemented   in   any   new   generation   js   framework   (e.g   VueJS, 
 AngularJS,   ReactJS)   as   a   SPA   application 

  
1.1   Hotel Feedback Details 
 
 
Fields: 
 -   Message: String 
 -   Title: String 
 -   Stay Type: String
 -   Filters: [String]
 -   User Id: Integer 
 -   Hotel Id : Integer
 -   Ratings: Map<String,Integre> 
  
Functionality: 
 -   ability   to   retrieve   feedback   details 
 -   ability   to   edit   feedback   details 
 -   the   system   should   store   only   one   single   hotel-feedback   (it   can   be   seeded) 
 -   crud   actions   for   feedback   manager 
 -   retriving feedback data with stay type filteration
 
 
1.2   Hotel-Rating Details 
 
 
Fields: 
 -   location: num
 -   value for money: num
 -   facilities: num
 -   hospitality: num
 -   cleanliness: num
 
 
Functionality: 
 -   retriving rating data with stay type filteration
 
 
Note: 
 -   All   required   fields   should   be   validated 
 
 
RESULT: 
 
 
In   order   to   complete   this   task   follow   the   instructions   below: 
 
-   Create   a   private   git   repository   on   GitHub 
-   Share   access   with   github   user   :   xxxx 
 
Please   note   that   all   changes   should   be   committed   progressively.   One   big   commit   is   frowned 
 upon.   We   should   be   able   to   easily   see   the   parts   you   coded   and   differentiate   it   from   framework 
 code. 
