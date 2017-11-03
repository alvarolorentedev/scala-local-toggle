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
use default files for loading configuration, it will try to load toggles from the following (first-listed are higher priority):

- system properties
- application.conf (all resources on classpath with this name)
- application.json (all resources on classpath with this name)
- application.properties (all resources on classpath with this name)
- reference.conf (all resources on classpath with this name)

2. Add a configuration to in the root of your project or file configured in instantiation with a HOCON complient configuration similar to this:

```hocon
{
  "toggles": [
    {
      "name": "NAME_OF_YOUR_TOGGLE",
      "production": true
    }, 
    {
      "name": "NAME_OF_OTHER_YOUR_TOGGLE",
      "production": false
    }
  ]
}
```

the boolean denotes the state of the toggle depending on the environment, and the name is the identifying name.

3. Use with the name identifier defined in the previous step

```scala
  if (toggle.isEnabled("NAME_OF_YOUR_TOGGLE")) {
    //your code under toggle here
  }
```

### LOGO
Check out the new logo that I created on <a href="http://logomakr.com" title="Logo Makr">LogoMakr.com</a> https://logomakr.com/6ETKtj