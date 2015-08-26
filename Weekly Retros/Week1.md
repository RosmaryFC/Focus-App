Sprint 1 : 8/16 - 8/22

**Goals for last Sprint**:

*Sarah* 
  - fix issue where list view of apps only toggles one app at a time (not all subsequent apps following in listview)
  - Constantly check which apps to be monitored without running out of memory
  - refactor code for service classes that were created
  - Create a listview of apps that are currently being monitored (for home screen)
  - check app log for lollipop
  
*Janneisy*
  - Work on team Logo
  - polish activities and make sure UI is consistent
  - linking activities with buttons located in menu/nav drawer as well as layouts
  - work on creating the navigation drawer/menu due 8/18
  
*Rosmary*
  - create listView for focus sessions created 8/17
  - save block session info into database due 8/20
  - create option to grab info from block session and use it with service 8/21
  - pass block info into service

**Goals for this Sprint**:

*Sarah*
  - work on app monitoring service to check block time and make notifications for various events (ex. when focus session is nearing)
  - make service run even if app is not running
  - change listview into recyclerview
  - organize code and flow
  
*Janneisy*
  - Worked on the navigation. (connecting parts of the app together)
  - improve UI/ UX.
  - Added navagation layouts and set up the fragment placehoders for the features.
  - Add logo icon to project. 
   
*Rosmary*
  - Fix isse with Database not registering final column. (due 8/25)
  - connect Block session to services (due 8/25)
  - Finalize Block Session part of app (due 8/25)
  - Work on Block Intensity Feature (due 8/27)

**Guiding Questions** (consider before the meeting):

  *  What went well?
    - Logo was completed, improvements on UI/UX were made
    - Was able to choose which apps to be blocked, fix toggle issue, constantly check which apps are being monitored (every 30 seconds), clean up code, create a listview of current apps to be monitored on home screen, and check app log for lollipop
    - Listview was created for block sessions, Database was created (not completed) for block sessions
    - Some layout re-modifcations had to be done for android version compatability. 
  *  What should we do differently next time?
    - Make more frequent pull requests to not push a big request at once. 
  *  What did we learn?
    - Setting more ideal timelines for the things to be accomplished
    - The things we are tackling now are more difficult and reaching out for help is crucial
    - The layouts for Block Sessions and App Monitor would have to be in fragments. 
  *  What still puzzles us?
    - Error in Block time Database where column is missing (not sure why)
    
 
**Team Member Analysis**:
If you did not meet your goals, how will this affect the progression of the product? What will you do differently to meet your goals this week?

  *  Team Member #1 - Sarah
    * Goals/Responsibilities Last Week:
      - fix issue where list view of apps only toggles one app at a time (not all subsequent apps following in listview)
      - Constantly check which apps to be monitored without running out of memory
      - refactor code for service classes that were created
      - Create a listview of apps that are currently being monitored (for home screen)
      - check app log for lollipop
        * Did you meet your goals/responsibilities last week? Why or why not?
        - Yes. 
    * Goals/Responsibilities This Week:
      - work on app monitoring service to check block time and make notifications for various events (ex. when focus session is nearing)
      - make service run even if app is not running
      - change listview into recyclerview
      - organize code and flow
  
  
  *  Team Member #2 - Janneisy
    * Goals/Responsibilities Last Week:
      - Work on team Logo
      - polish activities and make sure UI is consistent
      - linking activities with buttons located in menu/nav drawer as well as layouts
      - work on creating the navigation drawer/menu due 8/18
       * Did you meet your goals/responsibilities last week? Why or why not?
        - Issues with AppCompat and Lollipop
    * Goals/Responsibilities This Week:
      - Change AppMonitor from List View to Recycle View format.
      - Continue to polish UI as features are WIP.
      - Follow up w/ our mentor for recommendations.

  *  Team Member #3 - Rosmary
    * Goals/Responsibilities Last Week:
      - create listView for focus sessions created 8/17
      - save block session info into database due 8/20
      - create option to grab info from block session and use it with service 8/21
      - pass block info into service
        * Did you meet your goals/responsibilities last week? Why or why not?
        - No. I'm having issues with the database that I created for the block sessions where a column is missing although I have added it. So everytime a block session is added it won't appear due to the SQL error. Either than that the list view has been created and once the issue with the database is resolved, I can grab the information from the block session to use for the service
        - Not meeting my goals last week will affect the progression of the product by allowing less time to work on bonus features. I have 2 days less to work on the Block intensity part of the app.
    * Goals/Responsibilities This Week:
      - Fix isse with Database not registering final column. (due 8/25)
      - connect Block session to services (due 8/25)
      - Finalize Block Session part of app (due 8/25)
      - Work on Block Intensity Feature (due 8/27)


**Additional Discussion Topics**:

  *  Compare your work planned with what your work completed. 
    - Sarah completed all her goals
    - Since it was brought to our attention during the week that we had to finalize our logo Janneisy started creating sketches for those, and held off on working on the UI. Improvements to the UI were still made.
  *  Is the divison of roles within your team working?
    - Although we all have different roles (Services, Database, UI/UX) we still help each other on our parts.
  *  How is communication and collaboration between team members?
    - We have good comminucation and make team based decisions on things we are going to change in the app
  *  Are you getting adequate support? How are you reviewing code?
    - Prior to any pull request at least one of the team members must review the others code
  *  How can you improve productivity and get the most work done within the next week?
    - Setting deadlines. 
    - If help is needed reach out to team members and TA's.

**Mentor Comments**:
