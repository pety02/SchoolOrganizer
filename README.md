# Brief Description
Spring Boot monolithic Application, connected with time and tasks organising of the students. The application gives the users oppurtunity to sign up, login and maintan its own profile. The user can store its tasks, schedules and notebooks in their profile and to manage its user data.  
# Functionalities
CRUD operations on Schedules, Tasks, Notebooks and Profile panels. Storing data in SQL database - PostgresSQL. I decided to store the data in a SQL database because it is easier modifiable. Sign up, Sign in and Logout functionality.  
# DataBase
PostgresSQL database, created with the Code first approach via model classes, annotated with a java persistance annotations.

![DB](https://github.com/pety02/SchoolOrganizer/assets/47276102/acf745cb-8997-4869-8927-cc63439aa6e8)

## Entity classes:
### User
This class is an entity class for User objects. It stores id, name, surname, email, username, password, tasks, notebooks, friends, events and roles.
### Password
This class is an entity for Password objects. It stores id, passwordHash and owner. 
### Task
This class is an entity for Task objects. It stores id, title, startDate, finishDate, description, isFinished, files and createdBy.
### Notebook
This class is an entity for Notebook objects. It stores id, date, title, subject, sections and createdBy.
### File
This class is an entity for File objects. It stores id, date, name, artificialName, extension, path, addedInNotebookSections, addedInTasks.
### CalendarEvent
This class is an entity for CalendarEvent objects. It stores id, title, startDate, endDate, color and createdBy.
# Technologies
## Spring Boot + some Java starters
SpringBoot and Java starters are used for Back-end implementation.
## PostgresSQL
PostgresSQL is used for implementing a SQL database (Code-first approach) 
## Thymeleaf + Bootstrap
Thymeleaf and Bootstrap are used for front-end views implementation.
# Architecture
A monolithic MVC Spring Boot application.
## Project structure
### adapters
Interfaces and classes that adapts model classes to definite dtos and vice versa.
### dto
POJO objects that extract needed data to a definite view from the model classes.
### model
Entity classes, annotated with the java persistance annotations in oreder to generate the database tables.
### repository
Repository interfaces that extends generic JpaRepository interface and manage the database transactions.
### security
Classes conncted with the security (hashing passwords, user logged in validations and some configuration classes)
### service
Classes and interfaces connected with the service layer implementations.
### web
Controller classes for each entity with different mappings (Get, Post, Put/Patch, Delete) and endpoints of the API, connected with CRUD operations.
# Views
## Sign Up Form
![signup](https://github.com/pety02/SchoolOrganizer/assets/47276102/c20a2523-f1d4-4ff0-86b8-a07528433d92)
### Description
This view visualize the sign up form and provides the opportunity to sign up in the application.
## Sign In Form
![signin](https://github.com/pety02/SchoolOrganizer/assets/47276102/fa15c4ed-7ad2-4b4b-bf69-89faf2825e5a)
### Description
This view visualize the sign in form and provides the oppurtunity to sign in the application.
## Home Form
![home](https://github.com/pety02/SchoolOrganizer/assets/47276102/a17ca340-ef28-40d0-8f68-94dd0ddbd26a)
### Description
This view visualize the home page of the application after a successful login in the application. 
## Schedule Form - no events with this title
![no_events_with_this_title](https://github.com/pety02/SchoolOrganizer/assets/47276102/ee9e9444-2b59-46be-ac65-9cd18558c66b)
### Description
This view shows all events with this title if they exists, and a message if they do not exists.
## Schedule Form - events with this title information
![event_info](https://github.com/pety02/SchoolOrganizer/assets/47276102/5c5d6989-2eca-484f-b903-f872e84e4fdf)
### Description
## Tasks Form - no tasks
This view shows a message because this user has no tasks.
![no_tasks](https://github.com/pety02/SchoolOrganizer/assets/47276102/ab733264-609f-434f-a3d6-23d3be5756e7)
### Description
## Tasks Form - all tasks
This view shows all user's tasks.
![all_tasks](https://github.com/pety02/SchoolOrganizer/assets/47276102/0e141f4b-7d38-4af0-8b50-803c770ae022)
### Description
## Tasks Form - definite task information
![task_info](https://github.com/pety02/SchoolOrganizer/assets/47276102/2b4427e7-f51a-4977-85d4-5619622fe59f)
### Description
This view shows all definite task information.
## Notebooks Form - no notebooks
![no_notebooks](https://github.com/pety02/SchoolOrganizer/assets/47276102/88ff0c3d-4162-4732-93b5-4c28c37170a8)
### Description
This view shows a message because this user has no notebooks.
## Notebooks Form - all notebooks
![all_notebooks](https://github.com/pety02/SchoolOrganizer/assets/47276102/3e706e81-4cfb-455d-aa35-5dd42153ce48)
### Description
This view shows all user's notebooks.
## Notebooks Form - creating new notebook
![creating_notebook](https://github.com/pety02/SchoolOrganizer/assets/47276102/8ccfa75c-a11d-4010-b796-e6203466ad44)
### Description
This view shows new empty notebook that should be filled and sent in order to be saved in the database.
## Notebooks Form - sections
![notebook_sections](https://github.com/pety02/SchoolOrganizer/assets/47276102/7a3b8116-4805-4ecd-9287-233b6f8483fe)
### Description
This view shows all user's notebook's sections full data from the database.
## Profile Form - personal information
![edit_profile_data](https://github.com/pety02/SchoolOrganizer/assets/47276102/33e0ff2e-a7f7-4eb9-afb1-e1b75c252bd6)
### Description
This view shows the personal data panel and provides the oppurtunity to manage the personal data.
# Problems
## Security problems
Problems with the security layer. My security approach is connected with storing passwords in a different table not in the Users table. This is so not storing the password hash in the same table as the users because of the hackers attacks, better for GDPR, privacy regulations and so on. The application security problems are connected with proper authentication and authorization.
