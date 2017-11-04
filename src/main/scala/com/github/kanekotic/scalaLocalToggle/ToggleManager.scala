package com.github.kanekotic.scalaLocalToggle

import pureconfig.error.ConfigReaderFailures

case class Toggle(name: String, production: Boolean, development: Boolean, local: Boolean)
case class ToggleInfo(toggles: List[Toggle], environment: Option[String])

class ToggleManager(toggles : Either[ConfigReaderFailures, ToggleInfo], environmentWrapper: EnvironmentWrapper) {

  def this()
  {
    this(pureconfig.loadConfig[ToggleInfo]("feature.toggles"), new EnvironmentWrapper())
  }

  def isEnabled(toggleName : String) : Boolean = {
    if (IsError(toggles))
      return false
    val toggleConfig = toggles.right.get
    val environment = GetCurrentEnvironment(environmentWrapper, toggleConfig.environment)
    if(environment.equals(None) || !ToggleActive(toggleConfig.toggles, toggleName,environment.get))
      return false
    return true
  }

  object IsError extends (Either[Any,Any] => Boolean){
    override def apply(either: Either[Any, Any]) = {
      either.isLeft
    }
  }

  object GetCurrentEnvironment extends ((EnvironmentWrapper, Option[String]) => Option[String]){
    override def apply(environmentWrapper: EnvironmentWrapper, environment: Option[String]) = {
      environmentWrapper.get(environment.getOrElse("ENVIRONMENT"))
    }
  }

  object ToggleActive extends ((List[Toggle],String,String) => Boolean){
    override def apply(toggles: List[Toggle], toggleName : String, environmentName: String) = {
      var result = false
      val toggle = toggles collectFirst {case toggle if toggle.name == toggleName => toggle }
      if(!toggle.equals(None))
        environmentName match {
          case "PRODUCTION" => result = toggle.get.production
          case "DEVELOPMENT" => result = toggle.get.development
          case "LOCAL" => result = toggle.get.local
        }
      result
    }
  }
}
