package com.github.sguzman.scala.scraber

import com.github.sguzman.scala.scraber.args.Argv
import com.github.sguzman.scala.scraber.json.postuser.{UserAnswerMobile, UserHandleAnswerMobile, UserIdentifierMobile, UserMobile}
import io.circe.generic.auto._
import io.circe.syntax._

import scalaj.http.{Http, HttpResponse}

object Main {
  def main(args: Array[String]): Unit = {
    val argv = Argv(args)

    val url1 = "https://auth.uber.com/login/?next_url=https%3A%2F%2Fpartners.uber.com"
    val url2 = "https://auth.uber.com/login/handleanswer"
    val url3 = "https://auth.uber.com/login/handleanswer"
    val url4 = "https://auth.uber.com/login/handleanswer"

    val request = Http(url1)
    val response = request.asString

    val body = UserHandleAnswerMobile(
      UserAnswerMobile("VERIFY_INPUT_MOBILE",
        UserIdentifierMobile(
          UserMobile(argv.country, argv.user))),
      init = true)

    val bodyStr = body.asJson.noSpaces

    val response2 = post(response, bodyStr)

    println(response2.body)
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
}
