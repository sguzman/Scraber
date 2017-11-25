package com.github.sguzman.scala.scraber.args

import com.beust.jcommander.Parameter

class Argv {
  @Parameter(
    names = Array("-u", "--user", "--username"),
    description = "Uber username",
    arity = 1,
    echoInput = false,
    hidden = false,
    required = true,
    password = false,
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
    order = 2
  )
  var pass: String = ""
}
