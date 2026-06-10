# Normalizer Module

## Purpose

The Normalizer transforms raw permit records into a clean and standardized representation.

It consumes raw events and publishes normalized events.

## Responsibilities

### Event Consumption

Consumes:

```text
building-permit.raw
```

using consumer group:

```java
KafkaGroupIDs.NORMALIZER
```

### Data Normalization

Typical transformations include:

* trimming whitespace
* standardizing text values
* handling missing values
* converting source-specific formats
* mapping source fields to domain fields

### Event Publishing

Produces:

```text
building-permit.normalized
```

with payload:

```java
BuildingPermitNormalizedEvent
```

### Error Handling

Failed messages are routed to:

```text
building-permit.raw.dlq
```

## Event Flow

```text
building-permit.raw
        ↓
   Normalizer
        ↓
building-permit.normalized
```

## Technologies / Frameworks

* Java 25
* Spring Boot 4
* Spring Kafka
* Jackson

## Startup

```bash
mvn spring-boot:run -pl normalizer
```
