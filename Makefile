SHELL := /bin/bash
OS := $(shell uname)

define start-services
	@docker compose -f docker-compose.yaml up --force-recreate -d --remove-orphans sonar db kafka kafdrop sonar-db sonar fluentbit
endef

define start-check
	@docker compose -f sonar-compose.yaml up --force-recreate -d --remove-orphans sonar-db sonar
endef

define start-app
	@docker compose -f docker-compose.yaml up -d web worker
endef

define teardown
	@docker compose -f docker-compose.yaml rm -f -v -s
	@docker system prune -f --volumes
endef

define check-teardown
	@docker compose -f sonar-compose.yaml rm -f -v -s
	@docker system prune -f --volumes
endef

define local-setup
	$(call start-services)
endef

define local-app
	$(call start-app)
endef

define setup
	@docker compose -f docker-compose.yaml up -d --build --force-recreate --remove-orphans
endef

define del-local-app
    @docker stop craft-worker
    @docker stop craft-web
    @docker rm craft-worker
    @docker rm craft-web
    @docker image rm craft
    @docker image rm shubham01/craft
endef


.PHONY: help install setup teardown

help:
	@echo "######### Targets ##########"
	@echo "help: Display the help menu"
	@echo "install: Display the help menu"
	@echo "local-setup: Setup test resources locally"
	@echo "local-app: Setup test resources locally"
	@echo "format: Formats the java codebase"
	@echo "setup: Setup test resources"
	@echo "teardown: Destroy test resources"
	@echo "start-services: Start dependent services for testing"
	@echo "tests: Run tests in local"
	@echo "run-test: Run specific test"
	@echo "############################"

start-check:
	$(call start-check)

check-teardown:
	$(call check-teardown)

check:
	sudo ./gradlew sonar

local-setup: teardown
	$(call local-setup)

setup: teardown build
	$(call setup, "Setting up...")

start-services:
	$(call start-services)

teardown:
	$(call teardown, "Tearing down...")

run-test:
	@docker-compose -f docker-compose.yaml up --build --force-recreate -d service

migrations:

format:
	./gradlew format

install: setup

clean:
	clear
	./gradlew clean

build-local: clean
	./gradlew build

rm-images: clean
	docker image rm shubham01/craft
	docker image rm craft

docker-build:
	docker build -t shubham01/craft:latest .

build: clean build-local docker-build

rebuild: rm-images build

run-local: build-local
	./gradlew bootRun

run: build
	docker run -p 8080:8080 shubham01/craft:latest --network="host"

k8s-apply:
	$(call k8s-apply)

k8s-delete-app:
	$(call k8s-delete-app)

del-local-app:
	$(call del-local-app)

local-app: format build
	$(call local-app)

local-app-re: del-local-app local-app

coverage:
	sudo ./gradlew jacocoTestCoverageVerification

tests: local-setup
	sudo ./gradlew test
	make teardown

pipeline-build: local-setup
	./gradlew build
