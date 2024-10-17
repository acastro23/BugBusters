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
  
  Once the hotel is selected, the guset then has to select a date for which they want to check in. The same procedure as before follows. An string holds the date information, and that date is then checked for validation with an assertEqual() method.

