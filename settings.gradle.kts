rootProject.name = "kit"

include("app", "core", "user", "note")

project(":app").projectDir = file("backend/app")
project(":core").projectDir = file("backend/core")
project(":user").projectDir = file("backend/user")
project(":note").projectDir = file("backend/note")
