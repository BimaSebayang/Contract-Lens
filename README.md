# ContractLens

> **Observe. Detect. Protect.**

ContractLens is an intelligent API Gateway and Contract Intelligence
Platform that helps engineering teams detect, analyze, protect, and
preserve API compatibility while maintaining minimal runtime overhead.

------------------------------------------------------------------------

# Vision

> **Give developers confidence when deploying APIs.**

ContractLens enables backend services to evolve safely without breaking
existing API consumers.

------------------------------------------------------------------------

# Engineering Principles

## Functional Principles

-   API First
-   Contract First
-   Backward Compatibility
-   Rule-based Transformation
-   Asynchronous Contract Analysis
-   Runtime Compatibility Protection

## Non-Functional Principles

-   Performance First
-   Reactive Processing
-   Memory-first Execution
-   Minimal Runtime Overhead
-   Reliability
-   Scalability by Design
-   High Observability
-   Testability

------------------------------------------------------------------------

# Architecture Philosophy

ContractLens separates operational data from contract intelligence.

## Operational Domain

**Source of Truth:** PostgreSQL

Stores:

-   Route Configuration
-   Token Configuration
-   Workspace
-   API Registration
-   Gateway Configuration

## Contract Intelligence Domain

**Source of Truth:** MongoDB

Stores:

-   Contract Snapshot
-   Baseline
-   Comparison Result
-   Compatibility Plan
-   API History
-   Future AI Insights

MongoDB acts as the **Contract Intelligence Knowledge Base**.

------------------------------------------------------------------------

# Runtime Architecture

``` text
                     Client
                        │
                        ▼
              Spring WebFlux Gateway
                        │
                Compatibility Cache
                   (Caffeine)
                        │
                        ▼
             Compatibility Engine
                        │
                        ▼
                 Runtime Transformation
                        │
                        ▼
                 Target Service


─────────────────────────────────────────────

                  RabbitMQ
                      │
                      ▼
               Analyzer Service
                      │
                      ▼
          Contract Snapshot Engine
                      │
                      ▼
             Baseline Detection
                      │
                      ▼
            Contract Comparison
                      │
                      ▼
       Compatibility Plan Generation
                      │
                      ▼
                  MongoDB
```

------------------------------------------------------------------------

# Features

## API Gateway

-   Spring WebFlux
-   Reverse Proxy
-   Dynamic Route Resolution
-   PostgreSQL Route Management
-   Reactive Request Processing
-   Reactive Response Processing
-   Reliable RabbitMQ Publishing

## Contract Intelligence

-   Contract Snapshot
-   Baseline Detection
-   Contract Comparison
-   Compatibility Plan Generation
-   Runtime Compatibility
-   API History

## Compatibility Engine

-   Type Compatibility
-   Required Field Compatibility
-   JSON Structure Compatibility
-   Runtime Payload Transformation
-   Caffeine Local Cache

------------------------------------------------------------------------

# Roadmap

## Sprint 1 ✅

Foundation

-   Project Initialization
-   Multi Module Architecture
-   Docker
-   Common Library

------------------------------------------------------------------------

## Sprint 2 ✅

API Gateway

-   Reverse Proxy
-   Dynamic Route Resolution
-   PostgreSQL
-   Token Management
-   Flyway

------------------------------------------------------------------------

## Sprint 3 ✅

Reliable Event Processing

-   RabbitMQ
-   Retry
-   Outbox Pattern
-   Async Processing

------------------------------------------------------------------------

## Sprint 4 ✅

Contract Intelligence

-   Snapshot
-   Baseline
-   Comparison
-   History
-   MongoDB

------------------------------------------------------------------------

## Sprint 5 ✅

Gateway Enhancement

-   Dynamic Route Resolution
-   Read-through Cache
-   Route Management
-   Cache Rebuild

------------------------------------------------------------------------

## Sprint 6 🚧

### Goal

Build the Compatibility Engine while migrating the Gateway to Spring
WebFlux.

  Story                                           Status
  ----------------------------------------------- --------
  Story 6.0 --- Gateway Refactoring               🚧
  Story 6.1 --- Compatibility Engine Foundation   🚧
  Story 6.2 --- Data Type Compatibility           ⬜
  Story 6.3 --- Required Field Compatibility      ⬜
  Story 6.4 --- JSON Structure Compatibility      ⬜
  Story 6.5 --- Compatibility Cache (Caffeine)    ⬜
  Story 6.6 --- Compatibility Runtime             ⬜
  Story 6.7 --- Compatibility Validation          ⬜
  Story 6.8 --- Performance Optimization          ⬜
  Story 6.9 --- Sprint Validation                 ⬜

### Sprint 6 Technical Decisions

-   Spring WebFlux for Gateway runtime
-   MongoDB as Contract Intelligence Source of Truth
-   PostgreSQL as Operational Source of Truth
-   Caffeine as Local Compatibility Cache
-   Compatibility Plans loaded at Gateway startup
-   No runtime contract comparison
-   Memory-first execution
-   O(1) CompatibilityPlan lookup

------------------------------------------------------------------------

## Sprint Validation 🧪

Validation includes:

-   Unit Testing
-   Integration Testing
-   Functional Testing
-   Regression Testing
-   JVM Memory Profiling
-   Caffeine Benchmark
-   Garbage Collection Analysis
-   Throughput Benchmark
-   Latency Benchmark
-   Load Testing
-   Stress Testing

Target:

-   Average Overhead \< 20 ms
-   P95 \< 50 ms
-   P99 \< 100 ms

------------------------------------------------------------------------

## Sprint 7

Interactive Dashboard

------------------------------------------------------------------------

## Sprint 8

AI Contract Intelligence

------------------------------------------------------------------------

## Sprint 9

Enterprise Features

------------------------------------------------------------------------

# Current Status

## Completed

-   API Gateway
-   Dynamic Routing
-   RabbitMQ
-   Contract Snapshot
-   Baseline Detection
-   Contract Comparison
-   API History

## In Progress

-   Spring WebFlux Migration
-   Compatibility Engine Foundation

## Planned

-   Caffeine Runtime Cache
-   Runtime Compatibility
-   Performance Validation

------------------------------------------------------------------------

# Future Vision

ContractLens aims to become a complete **API Contract Intelligence
Platform** capable of:

-   Detecting API changes
-   Preserving backward compatibility
-   Executing runtime transformations
-   Explaining contract changes with AI
-   Recommending compatibility strategies
-   Providing enterprise-grade observability

------------------------------------------------------------------------

# Closing

> **Observe. Detect. Protect.**

ContractLens combines intelligent contract analysis, runtime
compatibility protection, and reactive engineering principles to help
teams deploy APIs with confidence.
