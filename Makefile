.PHONY: build run pipeline help

## Build the project
build:
	./mvnw install

## Run locally with H2 database (prototyping enabled)
run:
	PROTOTYPING=true ./mvnw -pl webapp spring-boot:run

## Run the CI pipeline locally
pipeline:
	./mvnw -B -ntp --fail-at-end -Dstyle.color=always verify


## Show all targets with descriptions
help:
	@grep -E '^##' -A1 Makefile | sed 's/## //'
