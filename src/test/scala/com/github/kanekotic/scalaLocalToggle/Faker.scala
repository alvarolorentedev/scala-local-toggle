package com.github.kanekotic.scalaLocalToggle

import java.util.UUID


object Faker {
  val random = scala.util.Random
  def RandomString : String = { UUID.randomUUID().toString }
  def RandomUrl : String = { "http://" + UUID.randomUUID().toString + ".com" }
  def RandomInt : Int = { random.nextInt() }

}