## Running the Noize Application on AppEngine

__Very Important: Read this First__

This example is built on top of Maven. You will need to 
install the Eclipse Maven plugin before continuing: http://www.eclipse.org/m2e/download/

After installing the Maven plugin for Eclipse, to import the project:
1. File->Import->Maven->Existing Maven Projects
2. Select the project that you cloned from the repo and import it

After importing the project, to run the application:

Right-click on pom.xml file and Run-As->Maven build...-> and use the "appengine:devserver"
goal. 

To access the local AppEngine admin API:

http://localhost:8080/_ah/admin

To stop the application:

Open the Eclipse Debug Perspective (Window->Open Perspective->Debug), right-click on
the application in the "Debug" view (if it isn't open, Window->Show View->Debug) and
select Terminate

To deploy the application to AppEngine:

1. Go to appspot.com and create a new application and take note of the "application identifier"
2. Open the appengine-web.xml in /src/main/webapp/WEB-INF and change <application>mobilecloudvideosvc</application> 
   to <application>Your-Application-Identifier</application>
3. Run-As->Maven build...-> and use the "appengine:update"
4. Maven will build your application and upload it to AppEngine
5. When the upload is complete, you should be able to access the videosvc at:
   http://0-1.<your-application-identifier>.appspot.com/video   
6. AppEngine provides a comprehensive administrative API that you should explore after launching your application.

## Accessing the Service

To view a list of the Members that have been added, open your browser to the following
URL:

http://localhost:8080/members

To add a test Member, run the MemberSvcClientApiTest by right-clicking on it in 
Eclipse->Run As->JUnit Test (make sure that you run the application first!)

## What to Pay Attention to

In this version of the VideoSvc application, we have built on top of Google AppEngine,
JDO, and Maven.

1. The VideoRepository is being manually implemented on top of JDOCrudRepository rather
   than using Spring Data
2. Maven provides automation to deploy the application to the cloud
3. The src/main/webapp/WEB-INF/web.xml file is being used to launch Spring and ensure that
   the Application class is used to configure the server. 