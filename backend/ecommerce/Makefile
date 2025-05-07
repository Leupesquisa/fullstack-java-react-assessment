# Makefile for E-commerce Monolith

# Variables
MAVEN = ./mvnw
DOCKER_COMPOSE = docker-compose -f docker/compose/dev/docker-compose.yml
DOCKER_BUILD = docker build -f docker/Dockerfile.backend -t ecommerce-monolith:latest .

# Default target
.PHONY: help
help:
	@echo "Available targets:"
	@echo "  help              - Show this help message"
	@echo "  clean             - Clean the project"
	@echo "  build             - Build the project"
	@echo "  test              - Run tests"
	@echo "  run               - Run the application with dev profile"
	@echo "  docker-build      - Build Docker image"
	@echo "  docker-up         - Start Docker services"
	@echo "  docker-down       - Stop Docker services"
	@echo "  docker-logs       - Show Docker logs"
	@echo "  docker-ps         - Show running Docker containers"
	@echo "  db-migrate        - Run database migrations"
	@echo "  db-clean          - Clean database (CAUTION: Deletes all data)"
	@echo "  all               - Clean, build, and test"

# Maven targets
.PHONY: clean
clean:
	$(MAVEN) clean

.PHONY: build
build:
	$(MAVEN) package -DskipTests

.PHONY: test
test:
	$(MAVEN) test

.PHONY: run
run:
	$(MAVEN) spring-boot:run -Dspring.profiles.active=dev

.PHONY: all
all: clean build test

# Docker targets
.PHONY: docker-build
docker-build:
	$(DOCKER_BUILD)

.PHONY: docker-up
docker-up:
	$(DOCKER_COMPOSE) up -d

.PHONY: docker-down
docker-down:
	$(DOCKER_COMPOSE) down

.PHONY: docker-logs
docker-logs:
	$(DOCKER_COMPOSE) logs -f

.PHONY: docker-ps
docker-ps:
	$(DOCKER_COMPOSE) ps

# Database targets
.PHONY: db-migrate
db-migrate:
	$(MAVEN) flyway:migrate -Dflyway.configFiles=src/main/resources/application.yml

.PHONY: db-clean
db-clean:
	@echo "WARNING: This will delete all data in the database."
	@read -p "Are you sure you want to continue? [y/N] " confirm; \
	if [ "$$confirm" = "y" ] || [ "$$confirm" = "Y" ]; then \
		$(MAVEN) flyway:clean -Dflyway.configFiles=src/main/resources/application.yml; \
	else \
		echo "Operation cancelled."; \
	fi