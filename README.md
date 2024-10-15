Group Project selection: We have decided on doing the “Online Reservation/Booking System for a Hotel”. It will consist of a mock website for a hotel that can take in reservations, look at currently available bookings, and submit payments for future reservations.

Project Description:

What types of users will use this system, and how? Our platform caters to travelers seeking a cozy, cabin-style hotel experience. Users can easily search for hotels based on their preferred location, browse available options, and check availability. Once they’ve found the perfect spot, they can book their stay directly through our website by securing their stay with a credit card, ensuring a seamless and enjoyable booking experience.

Room Descriptions What type of rooms: One type (2 queen beds, one bath.) The hotel will have five floors (Ex: 101 - first floor, first room. 205 - second floor, fifth room) 25 rooms per floor Calendar graphic to scroll through to see available booking dates and select dates to book List of amenities, i.e., television, minifridge, ice bucket, hair dryer, etc.

Payment Options The website only allows credit card payments. The website will contain an HTML Form to accept credit card payments where users must include the following:
First name, Last name 16-digit credit card number CVV 3-4 digits (depending on card) Expiration date: mm/yy (make sure it is not expired) If the user inputs the correct credit card information and the card isn’t expired, their payment will be accepted, allowing the user to book their reservation.

How will the system help its users accomplish their tasks? Users will first access the website's homepage, where they will have access to a navigation bar with several links. Users can click these links to view hotel locations, room descriptions, amenities, availability and book their stay. Once the user decides to book their stay, the website will prompt the user to select their form of payment to finalize their reservation. To ensure a user-friendly experience, when the user explores different parts of the website, the navigation bar will always be present to ensure that the user can access all parts of the website at all times.

Breakdown of the system: From the frontend, we will host the website through AWS as the server while using WordPress to create the website. Programming languages such as HTML, CSS, and JavaScript will assist us with website design and AJAX for accessing room availabilities. To create the backend of the project, we will use Java framework to handle the bulk of the code and AWS to help with the authentication of payments.

Database to store data: Tables: Guests Room Room Images Payment Bookings

Ideas and Brainstorming:

Project Ideas Payments (HTML Form) First name, Last name 16 digits CVV 3-4 digits (Depending on card) Expiration date: mm/yy (not expired) What type of rooms: Start with one type (2 queen beds, one bath.) 5 floors (Ex: 101 - first floor, first room. 205 - second floor, fifth room) 25 rooms per floor Little calendar graphic to scroll through to see available booking dates

Extra Ideas Different rooms (one bed, suite) Receipt after payment that can include if you qualify for free things depending on what room and how many days you book Amenities Depending on the date (Weekends, Holidays) make the payment more expensive Add email or SMS notifications for booking confirmations, payment recipts, and reminders before check in Use external payment processing services or implement stronger validation for payment details Identify credit card type based on card input. Display map of hotel locations using google maps api First digit: Visa (4), Mastercard (2 or 5), American Express (3), Discover (6). Login page: Users who book a stay can log in (username, password) to access information about their reservation. confirmation number
