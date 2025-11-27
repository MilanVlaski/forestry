.PHONY: build run pipeline run-prod-container publish deploy gar-deploy help gar-push

## Build the project
build:
	./mvnw install

clean:
	./mvnw clean

## Run locally with H2 database (prototyping enabled)
run:
	PROTOTYPING=true SPRING_PROFILES_ACTIVE=dev ./mvnw -pl webapp spring-boot:run

## Run the CI pipeline locally
pipeline:
	./mvnw -B -ntp --fail-at-end -Dstyle.color=always verify


## Deploy to App Engine.
## Make sure to export DEPLOYER_KEY.
deploy: GIT_SHA = $(shell git rev-parse --short HEAD)
deploy: BUILD_SUFFIX = $(if $(GITHUB_RUN_NUMBER),-$(GITHUB_RUN_NUMBER),)
deploy: export G_VERSION = $(GIT_SHA)$(BUILD_SUFFIX)
deploy:
	gcloud auth activate-service-account --key-file=$(DEPLOYER_KEY)
	./mvnw -pl webapp -am package appengine:deploy

## Sets the maven version to the latest git tag.
version: GIT_TAG = $(shell git describe --tags --abbrev=0 2>/dev/null || git rev-parse --short HEAD)
version:
	./mvnw versions:set -DnewVersion=$(GIT_TAG)

## Show all targets with descriptions
help:
	@grep -E '^##' -A1 Makefile | sed 's/## //'

## Push docker image to Google Artifact Registry using service account key in DEPLOYER_KEY
gar-push:
#	@if [ -z "$$DEPLOYER_KEY" ]; then echo "DEPLOYER_KEY is required (base64-encoded service account key JSON)"; exit 1; fi
#	@if [ -z "$$IMAGE" ]; then echo "IMAGE must be set (e.g., europe-west3-docker.pkg.dev/PROJECT/REPO/IMAGE:TAG)"; exit 1; fi
#	./mvnw -pl webapp -am -DskipTests package
#	@echo $$DEPLOYER_KEY | docker login -u _json_key --password-stdin https://$$(echo $(IMAGE) | awk -F/ '{print $$1}')
#	docker build -t $(IMAGE) -f Dockerfile .
#	docker push $(IMAGE)

## Publishes forestry-webapp docker image to Google Artifact Registry.
## Make sure to export REGISTRY_USERNAME and REGISTRY_PASSWORD.
#publish:
	#./mvnw -pl webapp -Dgar jib:build

## Deploys to Cloud Run.
## Make sure to export REGISTRY_USERNAME, REGISTRY_PASSWORD, DEPLOYER_KEY and ADMIN_PASSWORD.
#gar-deploy:
#	./mvnw -pl webapp -Dgar jib:build
#	gcloud auth activate-service-account --key-file=$(DEPLOYER_KEY)
#	gcloud run deploy forestry-webapp --image "$(IMAGE)" --region europe-west3 \
#	--set-env-vars ADMIN_PASSWORD="$(ADMIN_PASSWORD)",SPRING_PROFILES_ACTIVE=prod
