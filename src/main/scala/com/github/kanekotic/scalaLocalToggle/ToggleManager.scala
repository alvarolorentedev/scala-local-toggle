package com.github.kanekotic.scalaLocalToggle

import pureconfig.error.ConfigReaderFailures

case class Toggle(name: String, production: Boolean)
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
    if(environment.equals(None) || ToggleIsNotActive(toggleConfig.toggles, toggleName,environment.get))
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

  object ToggleIsNotActive extends ((List[Toggle],String,String) => Boolean){
    override def apply(toggles: List[Toggle], toggleName : String, environmentName: String) = {
      var result = false
      val toggle = toggles collectFirst {case toggle if toggle.name == toggleName => toggle }
      if(toggle.equals(None) || !toggle.get.production)
        result = true
      result
    }
  }
}
