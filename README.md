# ![logomakr_6etktj](https://user-images.githubusercontent.com/3071208/32364786-318debc0-c077-11e7-9064-a65d6ce35cf6.png)

feature toggle by configuration file for scala applications

## Install

```scala
libraryDependencies += "com.github.kanekotic" %% "scala-local-toggle" % "0.0.23"
```

##Use

1. Instantiate can be done by newing the class, it can also be register in guice or dependency injection frameworks.
```scala
  val toggle = new ToggleManager();
``` 

2. use default files for loading configuration, it will try to load toggles from the following (first-listed are higher priority):

- system properties
- application.conf (all resources on classpath with this name)
- application.json (all resources on classpath with this name)
- application.properties (all resources on classpath with this name)
- reference.conf (all resources on classpath with this name)

this files will require to have an setting for the toggles HOCON or JSON complient configuration similar to this, in any other case toggles will default to false:

```hocon
feature.local.toggles: [
    {
      "name": "NAME_OF_YOUR_TOGGLE",
      "local": true
      "development": true
      "production": false
    }, 
    {
      "name": "NAME_OF_OTHER_YOUR_TOGGLE",
      "local": true
      "development": false
      "production": false
    }
  ]
```
the boolean denotes the state of the toggle depending on the environment, and the name is the identifying the value of the environment variable:
- local maps to environment variable value LOCAL.
- development maps to environment variable value DEVELOPMENT.
- production maps to environment variable value PRODUCTION.

aditionally the enviroment variable that will be track to know the current environment of execution can be modified by
```hocon
  feature.local.environment: "SOME_ENVIROMENT_VARIABLE_NAME"
  #Defaults to ENVIRONMENT 
``` 

3. Use with the name identifier defined in the previous step

```scala
  if (toggle.isEnabled("NAME_OF_YOUR_TOGGLE")) {
    //your code under toggle here
  }
```

### LOGO
Check out the new logo that I created on <a href="http://logomakr.com" title="Logo Makr">LogoMakr.com</a> https://logomakr.com/6ETKtj