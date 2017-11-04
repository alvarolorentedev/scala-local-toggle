package com.github.kanekotic.scalaLocalToggle

import pureconfig.error.ConfigReaderFailures

case class Toggle(name: String, production: Boolean, development: Boolean, local: Boolean)
case class ToggleInfo(toggles: List[Toggle], environment: Option[String])

class ToggleManager(toggles : Either[ConfigReaderFailures, ToggleInfo], environmentWrapper: EnvironmentWrapper) {

  def this()
  {
    this(pureconfig.loadConfig[ToggleInfo](ToggleManager.OptionPath), new EnvironmentWrapper())
  }

  def isEnabled(toggleName : String) : Boolean = {
    if (IsError())
      return false
    val toggleConfig = toggles.right.get
    val environment = GetCurrentEnvironment(toggleConfig.environment)
    if(environment.equals(None) || !IsToggleActive(toggleConfig.toggles, toggleName,environment.get))
      return false
    return true
  }

  private[this] def IsError() : Boolean = {
      toggles.isLeft
  }

  private[this] def GetCurrentEnvironment(environment: Option[String]) : Option[String] = {
      environmentWrapper.get(environment.getOrElse(ToggleManager.Environment))
  }

  private[this] def IsToggleActive(toggles: List[Toggle], toggleName : String, environmentName: String) : Boolean = {
      var result = false
      val toggle = toggles collectFirst {case toggle if toggle.name == toggleName => toggle }
      if(!toggle.equals(None))
        environmentName match {
          case ToggleManager.Production => result = toggle.get.production
          case ToggleManager.Development => result = toggle.get.development
          case ToggleManager.Local => result = toggle.get.local
        }
      result
  }
}

object ToggleManager {
  final val OptionPath  ="feature.local"
  final val Environment =  "ENVIRONMENT"
  final val Production =  "PRODUCTION"
  final val Development =  "DEVELOPMENT"
  final val Local =  "LOCAL"
}
