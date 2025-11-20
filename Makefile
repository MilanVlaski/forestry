.PHONY: build run pipeline run-prod-container publish deploy help

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
	./mvnw -pl webapp compile jib:dockerBuild -Dimage=forestry-webapp:local
	docker run -p 8080:8080 -e ADMIN_PASSWORD=supersecret -e SPRING_PROFILES_ACTIVE=prod forestry-webapp:local

## Publishes forestry-webapp docker image to Google Artifact Registry.
## Make sure to export REGISTRY_USERNAME and REGISTRY_PASSWORD.
GIT_TAG := $(shell git describe --tags --abbrev=0 2>/dev/null || git rev-parse --short HEAD)
GIT_SHA := $(shell git rev-parse --short HEAD)
REVISION := $(GIT_TAG)-$(GIT_SHA)
IMAGE := europe-west3-docker.pkg.dev/forestry-webapp/forestry/webapp:$(REVISION)
export IMAGE
publish:
	./mvnw -pl webapp -Dgar jib:build

## Deploys to Cloud Run.
## Make sure to export REGISTRY_USERNAME, REGISTRY_PASSWORD, DEPLOYER_KEY and ADMIN_PASSWORD.
deploy:
	./mvnw -pl webapp -Dgar jib:build
	gcloud auth activate-service-account --key-file=$(DEPLOYER_KEY)
	gcloud run deploy forestry-webapp --image "$(IMAGE)" --region europe-west3 \
	--set-env-vars ADMIN_PASSWORD="$(ADMIN_PASSWORD)",SPRING_PROFILES_ACTIVE=prod,LOG_LEVEL=debug


## Show all targets with descriptions
help:
	@grep -E '^##' -A1 Makefile | sed 's/## //'
