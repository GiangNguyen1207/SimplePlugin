# SimplePlugin

Live demo: https://www.loom.com/share/7c5ab48cc92e474c95271d4c59a87522?sharedAppSource=personal_library

### Dependencies

1. Git4idea: Used to find the current working Git branch 

### How to set up and run

1. Clone the repo.
2. Click on the gradle tabl -> choose intellij -> runIde to run the plugin. 
3. It will open a new window to choose a project. Choose one.
4. Test the plugin. It is located in the tool menu, with the name "Open File on Github".

### Technical decision

1. The plugin should open the file in the current opening window instead of the current chosen file.
2. Alternative to find the current working branch instead of using another plugin.
3. Handling error of fetching data from Github API.
4. .gitignore file
5. Git4idea document?
