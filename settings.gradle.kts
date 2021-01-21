rootProject.name = "kit"

include("app", "core", "user")

project(":app").projectDir = file("backend/app")
project(":core").projectDir = file("backend/core")
project(":user").projectDir = file("backend/user")
