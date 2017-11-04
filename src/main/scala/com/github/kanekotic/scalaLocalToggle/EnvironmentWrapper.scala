package com.github.kanekotic.scalaLocalToggle

class EnvironmentWrapper {
  def get(name : String) : Option[String] = {
    sys.env.get(name)
  }
}
