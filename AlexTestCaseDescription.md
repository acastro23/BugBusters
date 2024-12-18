# Alex's Test Cases Breakdown

Alex Castro worked on test case scenario 2: 
```
"User selects hotel and date for stay. User selects room/rooms. User quits.
      1.) User can not book less than one room
      2.) User can not book more than five rooms
      3.) User can not book 2+ beds for a room
```

# TestScenario2_General
This class test the general testing scenario where a user will try to select a hotel, date, and room, then guest quits the application. The guest will first select their hotel and that is checked with a string with the name of our hotel. That string is then passed through and assertEqual() method to ensure that the string passed through matches the name of our hotel. As of right now, BugBusters plans on having one hotel. 
  
Once the hotel is selected, the guest then has to select a date for which they want to check in. The same procedure as before follows. A string holds the date information, and then the date is checked for validation with an assertEqual() method.

Guest then selects a room, for the general class, assume the guest is only looking to book one room. There are a few objects that hold room information such as room number, room floor, room type, and the availability status. The room information is passed through the assert method to ensure a match. Note, the availability is set to true and passed through and assertTrue() method, so if availability is set to false.

<u>Passing Screenshot:</u><br>

<img src="src/main/resources/TestScreenshots/TestScenario2GeneralScreenshot.png" alt="TestScenario2GeneralScreenshot" width="350">

# TestScenario2_Test1

```
User can not book less than one room
```

guest will attempt to book 0 rooms. This attempt makes no sense in practice, so an error should be thrown when the user attempts. A method(bookRooms()) will handle the logic for when a user attempts this through and if condition. 

If the number of rooms guest tries to book is less than one, then thrown an IllegalArgumentException. Now, in the test method, an int object holds the number of rooms the user is attempting to book. The object will then be passed through and assertThrows() method. Assume the object that holds number of room = 0, when its passed through the assertThrows() method, it calls on the bookRooms() method that handled the exception logic and will throw an error to the user as expected. Therefore, the case should pass when the object < 1, and it should fail if the object > 1 since no exception would get thrown at the user.

<u>Passing Screenshot: </u><br>
<img src="src/main/resources/TestScreenshots/TestScenario2_Test1_Passing.png" alt="TestScenario2_Test1 Passing Screenshot" width="650">

<br><u>Failing Screenshot: </u><br>
<img src="src/main/resources/TestScreenshots/TestScenario2_Test1_Failing.png" alt="TestScenario2_Test1 Failing Screenshot" width="650">
<p><u>Note:</u> The test fails when room is changed to an int >= 1 like in the failed test screenshot

# TestScenario2_Test2

```
User can not book more than five rooms
```

guest will attempt to book more than 5 rooms. This is a bit much for our relatively small hotel system, so an error should be thrown when the user attempts. A method(bookRooms()) will handle the logic for when a user attempts this through and if condition. 

If the number of rooms guest tries to book is more than five, then thrown an IllegalArgumentException. Now, in the test method, an int object holds the number of rooms the user is attempting to book. The object will then be passed through and assertThrows() method. Assume the object that holds number of room = 6, when its passed through the assertThrows() method, it calls on the bookRooms() method that handled the exception logic and will throw an error to the user as expected. Therefore, the case should pass when the object > 5, and it should fail if the object < 5 since no exception would get thrown at the user.

<u>Passing Screenshot: </u><br>
<img src="src/main/resources/TestScreenshots/TestScenario2_Test2_Passing.png" alt="TestScenario2_Test2 Passing Screenshot" width="600">

<u>Failing Screenshot: </u><br>
<img src="src/main/resources/TestScreenshots/TestScenario2_Test2_Failing.png" alt="TestScenario2_Test2 Failing Screenshot" width="600">
<p><u>Note:</u> No exception gets thrown since the user is booking an acceptable number of rooms, so test fails.

# TestScenario2_Test3

```
User can only select room after selecting valid type
```

A guest may want a Single room, which comes with one bed, or a double, two beds. After the guest chooses the type of room they want, then they can select all available rooms if the type they want is valid.

After the guest selects their room, that room then becomes unavailable.

<u> Code Demo:</u><br>
<img src="src/main/resources/TestScreenshots/TestScenario2_Test3_CodeDemo.png" alt="TestScenario2_Test3 Code Demo" width="550">
<p><u>Note:</u> The test will pass since "Single" is an acceptable room type

<br><u>Passing Screenshot: </u>

<img src="src/main/resources/TestScreenshots/TestScenario2_Test3_Passing.png" alt="TestScenario2_Test3 Passing Screenshot" width="295">

<u>Failing Test Screenshot:</u>

<img src="src/main/resources/TestScreenshots/TestScenario2_Test3_Failing.png" alt="TestScenario2_Test3 Failing Screenshot" width="295">
<p><u>Note:</u> I replaced "Single" with "Triple." The test fails since "Triple" is not an acceptable room type