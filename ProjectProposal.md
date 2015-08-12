
## **Focus-App (Work In Progress)**  
## **Team Focus (Work In Progress)**  
### **Rosemary Fermin, Sarah Kim, Janneisy Vidals**  

### The Problem 

 With the latest phones and gadgets out it has become habitual for users to be on their devices 24/7, affecting their daily lifestyles, which can negatively affect their productivity.
 
 During hours dedicated for study or work, smart phone users tend to get easily distracted by apps such as facebook, twitter, 9gag, instagram, vine etc. If there was a way to limit the use of certain apps during study or work hours then people would be more productive.

### The Solution 

Focus-app is an app that prevents users from using distracting apps during work hours, study hours, family time, or any time a focus session is needed. This will limit app usage, not just overall, but when limited app usage is needed the most.

Once the users have chosen the apps they want to restrict from within the focus-app, and set the intensity of the restrictions, they will be allowed to create Block times (or Focus sessions), where they can dedicate certain blocks of time to whatever is needed, and be restricted from using those distracting apps. These block times can be exported to a google calender so the user can see where it would lay in their own previously created schedule. 

Baseline features that will be implemented by Demo 1 (what + why).

 - app usage feature: needed for graphs (user feedback bonus feature), knowing when the user is on an app which will help determinine when to restrict the user from apps chosen.
      - will use permissions to read apps running on phone
      - will determine what app is running on main screen.
      - will create option where user can choose the apps that they want to monitor (located in settings).

 - Create/view Block times (also known as focus sessions) feature: User creates block times where they do not want to use certain apps on their phones and can export it to an already created schedule (such as google calendar). The app would monitor the focus session for the user to have limited app use. 
      - work with google calendar API (READ_CALENDAR, WRITE_CALENDAR)
      - will prevent users from accessing apps by opening up a focus-app activity if the user tries to open up one of the apps that they have chosen to restrict.

 - Intensity feature: 3 different intensities the user can set to determine how strict they want the app to be. 
      - Friendly: will send dialogue boxes every 5 minutes reminding them of their focus session until they leave the app. This intensity will give the user the option to stay in the app for 5 more minutes or leave the app completely.
      - Stern Parent:sends pop up dialogue boxes reminding them that they have set a blocked time. a countdown timer will begin where if the user is still on the app after a set amount of time (lets say 10 minutes) they  will get kicked off. In the event that they want to access the app again (lets say for a break) then a life can be used.
      - Drill Sargeant: will kick the user off an app once the blocked time begins and throughout the duration of the blocked time. Lives cannot be used.



Bonus features to be implemented if baseline features are completed in time (what + why).

Our app will help users become aware of their phone addiction by providing app usage feedback as well as time productivity feedback in the form of a graph.
 - User feedback feature: will have graphs that show overall productivity of the user. Will have pie chart of user expecatition of app usage vs actual app usage. Will also have suggestions spot at the end that lets user know where on their schedule they can improve their productivity.

There will be a feature for leaniency where the user will have 3 lives which they can use if they want to extend their time on an app or access a blocked app for a set amount of time. 
 - Lives feature: User will have 3 lives that they can use when they want to have access to an app during a blocked time. in order to gain a life back they have to be productive for a set amount of time, or complete an item on the todo list.


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
