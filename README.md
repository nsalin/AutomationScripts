# AutomationScripts

To run the test (GhostTest or AdoptionTest), you must add the environment variables for each browser, or just for the one on which the tests will run. 
Another step, that must be completed, is to add two parameters to the VM: serverToBeTestedUrl and seleniumBrowser;  your VM Options should like this(chrome browser is the default):
for ghost.org -> -ea -DserverToBeTestedUrl=https://ghost.org -DseleniumBrowser=chrome
for http://thetestroom.com/webapp/ -> -ea -DserverToBeTestedUrl=http://thetestroom.com -DseleniumBrowser=chrome

If you will run multiple times the GhostTest class, the website will become unresponsive, and you cannot login or create new users anymore for some period of time.
