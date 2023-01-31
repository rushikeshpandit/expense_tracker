package com.rushikesh.expense_tracker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
  info = @Info(
  title = "Code-First Approach (reflectoring.io)",
  description = "" +
    "Lorem ipsum dolor ...",
  contact = @Contact(
    name = "Reflectoring", 
    url = "https://reflectoring.io", 
    email = "petros.stergioulas94@gmail.com"
  ),
  license = @License(
    name = "MIT Licence", 
    url = "https://github.com/thombergs/code-examples/blob/master/LICENSE")),
  servers = @Server(url = "http://localhost:8102")
)
class OpenAPIConfiguration {
}