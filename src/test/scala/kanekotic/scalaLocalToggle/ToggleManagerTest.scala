package kanekotic.scalaLocalToggle

import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}
import pureconfig.error.ConfigReaderFailures

class ToggleManagerTest  extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter  {
  var toggles : Toggles = null
  var error : ConfigReaderFailures = null
  var environment : EnviromentWrapper = null

  before{
    toggles = mock[Toggles]
    error = mock[ConfigReaderFailures]
    environment = mock[EnviromentWrapper]
    when(environment.get("ENVIRONMENT")).thenReturn(Some("PRODUCTION"))
  }

  "is enabled" should "return false in case of error" in {
    val toggleFiles = Left[ConfigReaderFailures,Toggles](error)
    val manager = new toggleManager(toggleFiles,environment)
    manager.isEnabled(Faker.RandomString) should be (false)

  }

  "is enabled" should "return true in case of existing toggle" in {
    val toggle = mock[Toggle]
    val toggleName = Faker.RandomString
    when(toggle.name).thenReturn(toggleName)
    when(toggle.production).thenReturn(true)
    when(toggles.toggles).thenReturn(toggle :: Nil)
    val toggleFiles = Right[ConfigReaderFailures,Toggles](toggles)
    val manager = new toggleManager(toggleFiles,environment)
    manager.isEnabled(toggleName) should be (true)
  }

  "is enabled" should "return false in case of non existing" in {
    when(toggles.toggles).thenReturn(Nil)
    val toggleFiles = Right[ConfigReaderFailures,Toggles](toggles)
    val manager = new toggleManager(toggleFiles,environment)
    manager.isEnabled(Faker.RandomString) should be (false)
  }

  "is enabled" should "return false in case of existing but disabled" in {
    val toggle = mock[Toggle]
    val toggleName = Faker.RandomString
    when(toggle.name).thenReturn(toggleName)
    when(toggle.production).thenReturn(false)
    when(toggles.toggles).thenReturn(toggle :: Nil)
    val toggleFiles = Right[ConfigReaderFailures,Toggles](toggles)
    val manager = new toggleManager(toggleFiles,environment)
    manager.isEnabled(toggleName) should be (false)
  }

  "is enabled" should "return false in case of no environment defined" in {
    val toggle = mock[Toggle]
    val toggleName = Faker.RandomString
    when(toggle.name).thenReturn(toggleName)
    when(environment.get("ENVIRONMENT")).thenReturn(None)
    when(toggle.production).thenReturn(true)
    when(toggles.toggles).thenReturn(toggle :: Nil)
    val toggleFiles = Right[ConfigReaderFailures,Toggles](toggles)
    val manager = new toggleManager(toggleFiles,environment)
    manager.isEnabled(toggleName) should be (false)
  }


}
