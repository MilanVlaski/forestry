.PHONY: build run pipeline run-prod-container help

## Build the project
build:
	./mvnw install

## Run locally with H2 database (prototyping enabled)
run:
	PROTOTYPING=true ./mvnw -pl webapp spring-boot:run

## Run the CI pipeline locally
pipeline:
	./mvnw -B -ntp --fail-at-end -Dstyle.color=always verify

## Creates a docker image locally, and runs it, with admin name `secman-admin` and password 'supersecret'.
prod-container-run:
	./mvnw -pl webapp compile \
 	com.google.cloud.tools:jib-maven-plugin:dockerBuild \
 	-Dimage=forestry-webapp:local
	docker run -p 8080:8080 -e ADMIN_PASSWORD=supersecret -e SPRING_PROFILES_ACTIVE=prod forestry-webapp:local

## Show all targets with descriptions
help:
	@grep -E '^##' -A1 Makefile | sed 's/## //'
