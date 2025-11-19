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
publish:
	./mvnw -pl webapp -Dgar git-commit-id:revision jib:build

## Deploys to Cloud Run.
## Make sure to export REGISTRY_USERNAME, REGISTRY_PASSWORD and DEPLOYER_KEY.
IMAGE_NAME := $(shell ./mvnw -q help:evaluate -Dexpression=jib.to.image -DforceStdout)
TAG := $(shell ./mvnw -q help:evaluate -Dexpression=jib.to.image.tags.tag -DforceStdout | head -n1)
FULL_IMAGE := $(IMAGE_NAME):$(TAG)
deploy:
	gcloud auth activate-service-account --key-file=$(DEPLOYER_KEY)
	gcloud config set project forestry-webapp
	./mvnw -pl webapp -Dgar git-commit-id:revision jib:build
	gcloud run deploy forestry-webapp --image "$(FULL_IMAGE)" --region europe-west3

## Show all targets with descriptions
help:
	@grep -E '^##' -A1 Makefile | sed 's/## //'
