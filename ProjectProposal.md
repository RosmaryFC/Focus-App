
## **Focus-App (Work In Progress)**  
## **Team Focus (Work In Progress)**  
### **Rosemary Fermin, Sarah Kim, Janneisy Vidals**  

### The Problem 

Many phone users have a phone addiction, which makes it difficult for them to dedicate their time to other important priorities manage their time wisely. With the latest phones and gadgets out, it has become habitual for users to be on their devices 24/7, affecting their daily lifestyles. 

 Focus-App is an app that helps you minimize the use of your phone by allowing you to create a schedule, or import a previously made schedule (such as google calendar), where you can set specific times that you know you would not want to use your phone. This will allow users to limit themselves on specific apps and help manage their time to be more productive.

### The Solution 

Our app will help users become aware of their phone addiction by providing app usage feedback as well as time productivity feedback in the form of a graph. Users will be able to create or import their own schedules determining when they can and cannot use certain apps on their phones which will limit app usage not just overall but when limited app usage is needed the most. Users can also create a to-do list with to-do items in which they can also set specific times aside for these items where they cannot use certain apps on their phones. An intensity feature will allow the user to set how badly they want the app to kick them off an app (the three intensities would be Friendly, Stern Parent, or Drill sargeant). There will be a feature for leaniency where the user will have 3 lives which they can use if they want to extend their time on an app or access a blocked app for a set amount of time. 


Baseline features that will be implemented by Demo 1 (what + why).

 - app usage feature: needed for graphs (user feedback), knowing when the user is on an app which will help determinine when to kick the user off an app (depending on intensity).
      - will use permissions to read apps running on phone
      - will determine what app is running on main screen.
      - will create option where user can choose the apps that they want to monitor.
      - this will be done using services.

 - Schedule feature: User creates block times where they do not want to use certain apps on their phones and can export it to an already created schedule (can be on google calendar for example). From that schedule the app would monitor the restricted times for the user to have limited app use . 
      - work with google calendar (READ_CALENDAR)
      - not responsible for blocking apps

 - Intensity feature: 3 different intensities user can set to determine how strict they want the app to be. 
      - Friendly: will constantly send user notifications and pop up dialogue boxes reminding them on work they have to do during the blocked times.
      - Stern Parent:sends pop up dialogue boxes reminding them that they have set a blocked time. a countdown timer will begin where if the user is still on the app after a set amount of time (lets say 10 minutes) they  will get kicked off. In the event that they want to access the app again (lets say for a break) then a life can be used.
      - Drill Sargeant: will kick the user off an app once the blocked time begins and throughout the duration of the blocked time. Lives cannot be used.

Bonus features to be implemented if baseline features are completed in time (what + why).

 - User feedback feature: will have graphs that show overall productivity of the user. Will have pie chart of user expecatition of app usage vs actual app usage. Will also have suggestions spot at the end that lets user know where on their schedule they can improve their productivity.
 - Todo list / Goals feature: allows user to set things to do with a time and date deadline which they can block usage of their phone as well for that set amount of time. 
 - Lives feature: User will have 3 lives that they can use when they want to have access to an app during a blocked time. in order to gain a life back they have to be productive for a set amount of time, or complete an item on the todo list.
 - at a Glance feature: When user opens the app they will see events on the calender for that day as well as blocked times for that day.


Link to prototype - https://marvelapp.com/a68e1d

### Execution

| Sprint | Date | Tasks | 
|----|----|---|\
| 1 | Aug 7 - 9 | Brainstorm ideas, create userflows, and submit project proposal. |
| 2 | Aug 10 - 11 | User testing using prototypes for user feedback.Check-in on August 11th. |
| 3 | Aug 12 - 15 | Building the layout and assigning priority features. Check-in on August 14th. |
| 4 | Aug 16 - 20 | Complete priority features (userflows and user testing). |
| 5 | Aug 21 - 25 | Work on bonus features. |
| 6 | Aug 26 - 28 | Polish app, user testing and check for bug fixes. |
