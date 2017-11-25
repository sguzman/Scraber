package com.github.sguzman.scala.scraber

import com.github.sguzman.scala.scraber.args.Argv

import scalaj.http.Http

object Main {
  def main(args: Array[String]): Unit = {
    val argv = Argv(args)

    println("hello world")

    val url1 = "https://auth.uber.com/login/?next_url=https%3A%2F%2Fpartners.uber.com"
    val url2 = "https://auth.uber.com/login/handleanswer"
    val url3 = "https://auth.uber.com/login/handleanswer"
    val url4 = "https://auth.uber.com/login/handleanswer"

    val request = Http(url1)
    val response = request.asString

    println(response.body)
  }
}
