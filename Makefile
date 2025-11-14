.PHONY: build run-local run run-local-pg pipeline help

## Build the project
build:
	mvn install

## Run locally with H2 database (no prototyping)
run-local:
	mvn -pl webapp spring-boot:run

## Run locally with H2 database (prototyping enabled)
run:
	PROTOTYPING=true mvn -pl webapp spring-boot:run

## Run locally with PostgreSQL via docker compose
run-local-pg:
	docker compose up -d
	mvn -pl webapp spring-boot:run -Dspring-boot.run.profiles=local-pg

## Run the CI pipeline locally (mirror of ci.yml)
pipeline:
	mvn -B -ntp --fail-at-end -Dstyle.color=always verify

## Show all targets with descriptions
help:
	@grep -E '^##' -A1 Makefile | sed 's/## //'
