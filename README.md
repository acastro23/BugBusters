# Group 2 - “Bug Busters”
By: Nathan Dermer, Julie Luu, Alex Castro, Muhammad Nadeem, Sumeya Hussein

<u>Development start date</u>: September 1, 2024

<u>Development end date</u>: December 2, 2024

# Pre Development Planning
<u>What types of users will use this system, and how?</u><br>
&emsp;Our platform caters to travelers seeking a cozy, cabin-style hotel experience. 
Users can easily search for hotels based on their preferred location, 
browse available options, and check availability. Once they’ve found the perfect spot, 
they can book their stay directly through our website by securing their stay 
with a credit card, ensuring a seamless and enjoyable booking experience.

<u>Room Descriptions</u><br>
&emsp;What type of rooms: One type (2 queen beds, one bath.)
The hotel will have five floors 
(Ex: 101 - first floor, first room. 205 - second floor, fifth room) 25 rooms per floor
Calendar graphic to scroll through to see available booking dates and select dates to book
List of amenities, i.e., television, mini-fridge, ice bucket, hair dryer, etc.	

<u>Payment Options</u><br>
&emsp;The website only allows credit card payments. The website will contain an HTML Form to accept credit card payments where users must include the following:  
<ul>
<li>First name</li>
<li>Last name</li>
<li>16-digit credit card number</li>
<li>CVV (3-4 digits, depending on card)</li>
<li>Expiration date (mm/yy, make sure it is not expired)</li>
<li>Billing Address:
<ul>
<li>Street</li>
<li>City</li>
<li>State</li>
<li>ZIP code</li>
</ul>
</li>
</ul>
&emsp;If the user inputs the correct credit card information and the card isn’t expired, their payment will be accepted, allowing the user to book their reservation. 

<u>How will the system help its users accomplish their tasks?</u><br>
&emsp;Users will first access the website's homepage, where they will have access to a navigation bar
with several links. Users can click these links to view hotel locations, room descriptions, amenities, availability and book their stay. 
Once the user decides to book their stay, the website will prompt the user to
select their form of payment to finalize their reservation. 
To ensure a user-friendly experience, when the user explores different parts of the website,
the navigation bar will always be present to ensure that the user can access all
parts of the website at any times.

<u>Breakdown of the system:</u><br>
&emsp;From the frontend, we will host the website through AWS as the server while using WordPress
to create the website. Programming languages such as HTML, CSS, and JavaScript will assist
us with website design and AJAX for accessing room availability.
To create the backend of the project, we will use Java to handle the bulk of the code and
AWS to help with the authentication of payments. 

<u>Hotel_manager Database:</u><br>

<ul>
<li><b>Guest</b>(guestID, fName, lName, email, phoneNumber)</li>
<li><b>Rooms</b>(roomID, roomNumber, floor, type, availability, description)</li>
<li><b>RoomImages</b>(imageID, roomID, imageURL)</li>
<li><b>Bookings</b>(bookingID, guestID, roomID, checkInTime, checkOutTime)</li>
<li><b>Payment</b>(paymentID, guestID, cardNumber, cvv, expDate, paymentDate)</li>
<li><b>Confirmation</b>(confirmationID, bookingID, confNum)</li>
</ul>

# Development phase
&emsp; Backend development started in early October and ended in late November. The development
was done by Alex Castro and Nathan Dermer. Several layers and packages were used such as
Entity, service, repository, exceptions, controller, and DTOs. The backend was developed using java
and the Spring boot framework.

&emsp; The database was also established early on in development. For our application, PostreSQL is
utilized and hosted on Supabase, and there were six tables made for the project initially.
After development, one table went unused as its use was not needed as development went along.

&emsp; This project used the Spring Boot framework for our web application as it allows for smooth
API development and provides many dependencies to help with web development.  
Four dependencies were initially created with the project with one being added later on. 
Those dependencies were: Spring Web, Spring Data JPA, PostgreSQL driver, Lombok, and starter-validation

&emsp;Front-end development was done by Julie Luu and Sumeya Hussein. Development started
in late october and ended in early December. The front-end used tools such as HTML, CSS, and 
Javascript. The backend created endpoints that the front-end would fetch using javascript
to create a clean and function webpage that allowed users to stay at our hotel. The front-end
was responsible for sending information to the backend as a JSON and the backend would validate
the information and store in the database.

# Running the application
In order to run the application, pull from the main branch and run the 
<b><SWEBackendApplication/b> class to run the app. Once the app is running, use this link http://localhost:8080/home.html
to head to our home page. From there users will be able to make bookings, check their booking details, cancel bookings,
and look at the founders of the site.
