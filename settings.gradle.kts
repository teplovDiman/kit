rootProject.name = "kit"

include("app", "user")

project(":app").projectDir = file("backend/app")
project(":user").projectDir = file("backend/user")
