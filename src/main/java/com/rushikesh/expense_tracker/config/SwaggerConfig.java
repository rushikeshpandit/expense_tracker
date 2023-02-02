package com.rushikesh.expense_tracker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
  info = @Info(
  title = "Expenses Tracker by Rushikesh Pandit",
  description = "These API's are useful for tracking user's expenses",
  contact = @Contact(
    name = "Rushikesh Pandit", 
    url = "http://rp-portfolio.s3-website.ap-south-1.amazonaws.com/", 
    email = "rushikesh.d.pandit@gmail.com"
  ),
  license = @License(
    name = "MIT Licence", 
    url = "https://github.com/thombergs/code-examples/blob/master/LICENSE")),
  servers = @Server(url = "http://localhost:8102")
)
class OpenAPIConfiguration {
}