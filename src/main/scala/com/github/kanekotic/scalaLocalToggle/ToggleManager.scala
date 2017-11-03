package com.github.kanekotic.scalaLocalToggle

import pureconfig.error.ConfigReaderFailures

case class Toggle(name: String, production: Boolean)
case class Toggles(toggles: List[Toggle])

class ToggleManager(toggles : Either[ConfigReaderFailures, Toggles], enviromentWrapper: EnviromentWrapper) {
  def this()
  {
    this(pureconfig.loadConfig[Toggles], new EnviromentWrapper())
  }

  def isEnabled(toggleName : String) : Boolean = {
    if(toggles.isLeft || enviromentWrapper.get("ENVIRONMENT") == None || !toggles.right.get.toggles.exists{ toggle => toggle.name == toggleName && toggle.production})
      return false
    return true
  }
}