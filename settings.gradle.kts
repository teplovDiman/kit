rootProject.name = "kit"

include("app", "core", "user", "server")

project(":app").projectDir = file("backend/app")
project(":core").projectDir = file("backend/core")
project(":user").projectDir = file("backend/user")
project(":server").projectDir = file("backend/server")
