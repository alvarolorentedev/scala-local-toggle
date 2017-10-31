package kanekotic.scalaLocalToggle

import java.nio.file.Paths

import pureconfig.error.ConfigReaderFailures
import pureconfig.loadConfigFromFiles

case class Toggle(name: String, production: Boolean)
case class Toggles(toggles: List[Toggle])

class toggleManager(toggles : Either[ConfigReaderFailures, Toggles], enviromentWrapper: EnviromentWrapper) {
  def this(path: String)
  {
    this(loadConfigFromFiles[Toggles](Seq(Paths.get(path))), new EnviromentWrapper())
  }

  def this()
  {
    this("toggles.conf")
  }

  def isEnabled(toggleName : String) : Boolean = {
    if(toggles.isLeft || enviromentWrapper.get("ENVIRONMENT") == None || !toggles.right.get.toggles.exists{ toggle => toggle.name == toggleName && toggle.production})
      return false
    return true
  }
}