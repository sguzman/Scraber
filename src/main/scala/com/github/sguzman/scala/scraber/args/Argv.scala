package com.github.sguzman.scala.scraber.args

import com.beust.jcommander.{JCommander, Parameter}

class Argv {
  @Parameter(
    names = Array("-u", "--user", "--username"),
    description = "Uber username",
    arity = 1,
    echoInput = false,
    hidden = false,
    required = true,
    password = false,
    help = false,
    order = 1
  )
  var user: String = ""

  @Parameter(
    names = Array("-p", "--pass", "--password"),
    description = "Uber password",
    arity = 1,
    echoInput = true,
    hidden = false,
    required = true,
    password = true,
    help = false,
    order = 2
  )
  var pass: String = ""

  @Parameter(
    names = Array("-c", "--country", "--countryCode"),
    description = "Phone number country code",
    arity = 1,
    echoInput = true,
    hidden = false,
    required = false,
    password = false,
    help = false,
    order = 3
  )
  var country: String = ""

  @Parameter(
    names = Array("-h", "--help", "--helpme"),
    description = "Display help menu",
    arity = 0,
    echoInput = true,
    hidden = false,
    required = false,
    password = false,
    help = true,
    order = 4
  )
  var help: Boolean = false
}

object Argv {
  def apply(args: Array[String]): Argv = {
    val argv = new Argv
    val j = JCommander
      .newBuilder
      .addObject(argv)
      .build

    j.parse(args: _*)
    if (argv.help) {
      j.usage()
      System.exit(0)
    }

    argv
  }
}
