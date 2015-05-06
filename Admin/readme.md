Follow the steps below:

Setup Apache Tomcat on your Amazon EC2 instance.
Usually all you have to do is download the current version, unzip it, and start it by running apache-tomcat-folder\bin\startup.bat. (You can also donwload an installer and set it up as windows service. Check this link for more details).
Make sure you test it before continuing (open its address on a browser, something like http://yourinstaceaddress.com:8080/).
Export your web application .war file
In Eclipse, right click on a Web project and select Export. Then select WAR file in the Export window and then select Next. Choose the project, the .war file name and folder to export. More detailed explanation can be found here and here (with pictures).
Deploy the .war file to your Tomcat Server
The, by far, simplest way to do this is to place your .war (say myapp.war) file in your apache-tomcat-folder\webapps\ folder.
There are other ways, like via Tomcat Manager. But they can be tricky and, as a new user, you should avoid them. (Don't worry: the simple method is ok for production deployment).
Test your web app
Visit the url: say your .war's name was myapp.war. You should visit http://yourinstaceaddress.com:8080/myapp
That's it. If you ever edit the app, repeat steps 2-4 (but delete the webapps\myapp\ folder created before executing step 3).

https://hc.apache.org/downloads.cgi

http://www.json.org/java/