package com.github.kanekotic.scalaLocalToggle

private[scalaLocalToggle] class EnvironmentWrapper {
  def get(name : String) : Option[String] = {
    sys.env.get(name)
  }
}
