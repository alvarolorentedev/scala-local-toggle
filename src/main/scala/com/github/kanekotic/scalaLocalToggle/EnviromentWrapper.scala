package com.github.kanekotic.scalaLocalToggle

class EnviromentWrapper {
  def get(name : String) : Option[String] = {
    sys.env.get(name)
  }
}
