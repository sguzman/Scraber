package com.github.sguzman.scala.scraber

import com.github.sguzman.scala.scraber.args.Argv
import com.github.sguzman.scala.scraber.json.request.password.{Answer, Password}
import com.github.sguzman.scala.scraber.json.request.sms
import com.github.sguzman.scala.scraber.json.request.user.{UserAnswerMobile, UserHandleAnswerMobile, UserIdentifierMobile, UserMobile}
import io.circe.generic.auto._
import io.circe.syntax._

import scalaj.http.{Http, HttpResponse}

object Main {
  def main(args: Array[String]): Unit = {
    val argv = Argv(args)

    val response = get
    val body = UserHandleAnswerMobile(
      UserAnswerMobile("VERIFY_INPUT_MOBILE",
        UserIdentifierMobile(
          UserMobile(argv.country, argv.user))),
      init = true)

    val bodyStr = body.asJson.noSpaces
    val response2 = post(response, bodyStr)

    val body2 = Password(Answer("VERIFY_PASSWORD", argv.pass), rememberMe = true)
    val bodyStr2 = body2.asJson.noSpaces
    val response3 = post(response2, bodyStr2)

    print("Enter SMS: ")
    val input = scala.io.StdIn.readLine
    val body3 = sms.Sms(sms.Answer("SMS_OTP", input))
    val bodyStr3 = body3.asJson.noSpaces
    val response4 = post(response3, bodyStr3)

    println(response4.statusLine)
    println(response4.body)
  }

  def post(lastResponse: HttpResponse[String], body: String) = {
    val url = "https://auth.uber.com/login/handleanswer"

    val request = Http(url)
      .postData(body)
      .header("Cookie", lastResponse.cookies.mkString("; "))
      .header("Content-Type", "application/json")
      .header("x-csrf-token", lastResponse.header("x-csrf-token").get)
    val response = request.asString

    response
  }

  def get = {
    val url = "https://auth.uber.com/login/?next_url=https%3A%2F%2Fpartners.uber.com"
    val request = Http(url)
    val response = request.asString

    response
  }
}
