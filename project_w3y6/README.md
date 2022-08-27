#NHL Sports Analytics Application
##Player Analysis through Team Statistics

**What will the application do?**

The purpose of this application will be to take user inputted NHL player data and convert it into 
new statistics which can be returned and compared.  This will be similar to the suggested "Fantasy 
Sports Team" example, but is a prior interest of mine and something I plan to eventually develop further.
In particular, I would like to create a program which can generate its own data from the web which can then 
be searched.  I am keeping it simple for this class though, as I do not want to bite off more than I can chew
at the moment.

This application will take user inputted:  
- *NHL Player name*
- *NHL Team name*
- *Year*
- *Player Games Played*
- *Player Wins*
- *Team Games Played*
- *Team Wins*

to calculate and return various statistics.  As sports data is constantly
updated, future implementations of this project will involve pulling web data.  As mentioned though, for this class
the project will only involve user inputted data.

The application will return a visualization of the statistics allowing comparison across 
multiple players.  Future application will provide deeper comparison and extend 
to multiple sports.

**Who will use it?**

This application is largely intended for personal use as a sports analytics tool.  If successful, it 
would be possible to make it accessible to others interested in sports analytics.

**Why is this project of interest to you?**

This is based on a personal project of mine from several years ago which was never fully developed
(or coded in any way).  It has always been my intent to fully develop it, but until now
I have not had the ability or opportunity.  While the scope of the full project extends beyond the
requirements of this class, I see this as an opportunity to establish a foundation which I
can later build upon.

##User Stories

- As a user, I want to be able to view a list of teams.
- As a user, I want to be able to create a team and add it to a list of teams.
- As a user, I want to be able to edit an existing team.
- As a user, I want to be able to delete a team from a list of teams.
- As a user, I want to be able to view a list of players and their statistics on a team.
- As a user, I want to be able to create a player and add it to a team.
- As a user, I want to be able to edit an existing player.
- As a user, I want to be able to copy a player from a team.
- As a user, I want to be able to delete an existing player from a team.

- As a user, I want to be able to save my teams to file (including all teams and players).
- As a user, I want to be able to load my teams from file (including all teams and players).

*NOTE 1: Due to the large number of user inputs require for a player's statistics, some methods were over the 25 line
limit and therefore required the @SuppressMethod("methodlength") annotation.  I feel this is justified as the additional 
inputs are a result of the project structure and not inefficient coding.*

*NOTE 2: The AccountNotRobust-TellerApp.java application was used as a reference for the Phase 1 UI implementation.*

*NOTE 3: The JsonSerializationDemo - JsonReader.java application was used as a reference for the Phase 2 data 
persistence implementation.*

*NOTE 4: The AlarmControllerUI.java application was used as a reference for the Phase 3 GUI.*

##Phase 4: Task 2

*Sat Mar 26 17:22:58 PDT 2022*\
*Added player joe to Player List*\
*Sat Mar 26 17:23:02 PDT 2022*\
*Added player joe to Player List*\
*Sat Mar 26 17:23:13 PDT 2022*\
*Added player frank to Player List*\
*Sat Mar 26 17:23:15 PDT 2022*\
*Added player frank to team1*\
*Sat Mar 26 17:23:24 PDT 2022*\
*Removed player joe from Player List*

*Process finished with exit code 0*

##Phase 4: Task 3

Future Refactoring:

- The association between SportsAnalyticsAppGUI and Team is likely unnecessary and increases coupling.  I would want to remove this association to decrease coupling.
- The GUI has a lot of associations, so its cohesion could likely be increased.  At the moment its actions include printing, persistence, and initialization.  Separating these actions into separate classes would increase cohesion.