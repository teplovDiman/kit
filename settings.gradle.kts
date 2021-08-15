rootProject.name = "kit"

include("app", "core", "user", "note", "server")

project(":app").projectDir = file("backend/app")
project(":core").projectDir = file("backend/core")
project(":user").projectDir = file("backend/user")
project(":note").projectDir = file("backend/note")
project(":server").projectDir = file("backend/server")
