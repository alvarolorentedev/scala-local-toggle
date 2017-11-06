package com.github.kanekotic.scalaLocalToggle

import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import pureconfig.error.ConfigReaderFailures

class ToggleManagerTest  extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfterEach  {
  var toggleConfig : Either[ConfigReaderFailures,ToggleInfo] = null
  var togglesInfo : ToggleInfo = null
  var environment : EnvironmentWrapper = null
  var toggle : Toggle = null
  var toggleName : String = null

  override def beforeEach{
    environment = mock[EnvironmentWrapper]
    when(environment.get("ENVIRONMENT")).thenReturn(Some("PRODUCTION"))

    toggleName = Faker.RandomString

    toggle = mock[Toggle]
    when(toggle.name).thenReturn(toggleName)
    when(toggle.production).thenReturn(true)
    when(toggle.development).thenReturn(false)
    when(toggle.local).thenReturn(false)

    togglesInfo = mock[ToggleInfo]
    when(togglesInfo.environment).thenReturn(Some("ENVIRONMENT"))
    when(togglesInfo.toggles).thenReturn(toggle :: Nil)

    toggleConfig = Right[ConfigReaderFailures,ToggleInfo](togglesInfo)

  }

  "is enabled" should "return false in case of error" in {
    val toggleFiles = Left[ConfigReaderFailures,ToggleInfo](mock[ConfigReaderFailures])
    val manager = new ToggleManager(toggleFiles,environment)
    manager.isEnabled(Faker.RandomString) should be (false)

  }

  "is enabled" should "return true in case of existing toggle" in {
    toggleConfig = Right[ConfigReaderFailures,ToggleInfo](togglesInfo)
    val manager = new ToggleManager(toggleConfig,environment)
    manager.isEnabled(toggleName) should be (true)
  }

  "is enabled" should "return false in case of non existing" in {
    when(togglesInfo.toggles).thenReturn(Nil)
    val manager = new ToggleManager(toggleConfig,environment)
    manager.isEnabled(Faker.RandomString) should be (false)
  }

  "is enabled" should "return false in case of existing but disabled" in {
    when(toggle.production).thenReturn(false)
    val manager = new ToggleManager(toggleConfig,environment)
    manager.isEnabled(toggleName) should be (false)
  }

  "is enabled" should "return true in case of different environment defined" in {
    when(togglesInfo.environment).thenReturn(Some("PEPE_ENVIRONMENT"))
    when(environment.get("PEPE_ENVIRONMENT")).thenReturn(Some("PRODUCTION"))
    when(environment.get("ENVIRONMENT")).thenReturn(None)
    when(togglesInfo.toggles).thenReturn(toggle :: Nil)
    val manager = new ToggleManager(toggleConfig,environment)
    manager.isEnabled(toggleName) should be (true)
  }

  "is enabled" should  "return false in case of no environment defined" in {
    when(environment.get("ENVIRONMENT")).thenReturn(None)
    val manager = new ToggleManager(toggleConfig,environment)
    manager.isEnabled(toggleName) should be (false)
  }

  "is enabled" should  "return true in case of environment is development and its enable" in {
    when(environment.get("ENVIRONMENT")).thenReturn(Some("DEVELOPMENT"))
    when(toggle.production).thenReturn(false)
    when(toggle.development).thenReturn(true)
    val manager = new ToggleManager(toggleConfig,environment)
    manager.isEnabled(toggleName) should be (true)
  }

  "is enabled" should  "return false in case of environment is development and its disable" in {
    when(environment.get("ENVIRONMENT")).thenReturn(Some("DEVELOPMENT"))
    when(toggle.production).thenReturn(false)
    when(toggle.development).thenReturn(false)
    val manager = new ToggleManager(toggleConfig,environment)
    manager.isEnabled(toggleName) should be (false)
  }

  "is enabled" should  "return true in case of environment is local and its enable" in {
    when(environment.get("ENVIRONMENT")).thenReturn(Some("LOCAL"))
    when(toggle.production).thenReturn(false)
    when(toggle.local).thenReturn(true)
    val manager = new ToggleManager(toggleConfig,environment)
    manager.isEnabled(toggleName) should be (true)
  }

  "is enabled" should  "is not case sensitive" in {
    when(environment.get("ENVIRONMENT")).thenReturn(Some("local"))
    when(toggle.production).thenReturn(false)
    when(toggle.local).thenReturn(true)
    val manager = new ToggleManager(toggleConfig,environment)
    manager.isEnabled(toggleName) should be (true)
  }

  "is enabled" should  "return false in case of environment is local and its disable" in {
    when(environment.get("ENVIRONMENT")).thenReturn(Some("LOCAL"))
    when(toggle.production).thenReturn(false)
    when(toggle.local).thenReturn(false)
    val manager = new ToggleManager(toggleConfig,environment)
    manager.isEnabled(toggleName) should be (false)
  }

}
