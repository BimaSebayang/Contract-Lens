# ContractLens

> **Observe. Detect. Protect.**

ContractLens is a self-hosted **API Gateway and Contract Intelligence Platform** designed to help engineering teams detect API changes, analyze compatibility, protect existing consumers, and evolve backend services safely.

At its core, ContractLens is built around three primary capabilities:

```text
OBSERVE
   │
   ├── API Gateway
   │
   └── Contract Analyzer
          │
          ▼
DETECT
   │
   └── Contract Intelligence
          │
          ▼
PROTECT
   │
   └── Compatibility Engine
```

**CLAra — ContractLens AI Robot Assistant — is an interaction layer built on top of the ContractLens platform.**

CLAra does not replace the Gateway, Analyzer, or Compatibility Engine. It helps users understand, access, and interact with the intelligence already provided by ContractLens.

---

# Vision

> **Give developers confidence when deploying APIs.**

ContractLens enables backend services to evolve safely without breaking existing API consumers.

The platform observes API traffic, captures and analyzes contract changes, determines compatibility impact, and provides runtime protection where compatibility strategies are available.

---

# What Is ContractLens?

Modern APIs evolve continuously.

A seemingly simple change can affect existing consumers:

```text
Old API
──────────────────────
amount: Integer

New API
──────────────────────
amount: String
```

Other changes may include:

- Request Body Changes
- Response Body Changes
- Header Changes
- Required Field Changes
- JSON Structure Changes

Without visibility into those changes, an API producer may deploy a new version while existing consumers continue expecting the previous contract.

ContractLens is designed to address that problem.

```text
                  API Ecosystem
                        │
                        ▼
              ┌─────────────────┐
              │   ContractLens  │
              └─────────────────┘
                 │      │      │
                 ▼      ▼      ▼
              Observe Detect Protect
```

---

# Core Platform Architecture

```text
                         Client
                           │
                           ▼
                ┌─────────────────────┐
                │   ContractLens      │
                │     Gateway         │
                └─────────────────────┘
                           │
                    ┌──────┴──────┐
                    │             │
                    ▼             ▼
              Runtime Path    Event Publishing
                    │             │
                    ▼             ▼
             Target Service     RabbitMQ
                                      │
                                      ▼
                            ┌─────────────────┐
                            │ Contract        │
                            │ Analyzer        │
                            └─────────────────┘
                                      │
                                      ▼
                            Contract Intelligence
                                      │
                                      ▼
                            Compatibility Engine
                                      │
                                      ▼
                            Compatibility Plan
                                      │
                                      ▼
                                   MongoDB
```

The Gateway, Contract Analyzer, and Compatibility Engine have different responsibilities but work together to support safe API evolution.

---

# API Gateway

The ContractLens Gateway is the operational entry point for registered APIs.

Its responsibilities include:

- Reverse Proxy
- Dynamic Route Resolution
- Route Management
- Token Configuration
- Reactive Request Processing
- Reactive Response Processing
- Reliable Event Publishing
- Runtime Compatibility Protection
- Runtime Payload Transformation
- Low-overhead execution

The Gateway is designed around:

```text
Client
  │
  ▼
Spring WebFlux Gateway
  │
  ▼
Route Resolution
  │
  ▼
Compatibility Cache
  │
  ▼
Compatibility Engine
  │
  ▼
Runtime Transformation
  │
  ▼
Target Service
```

## Example

Imagine a client sends a request to:

```http
GET /api/payment/123
```

The Gateway resolves the registered route:

```text
/api/payment/**
        │
        ▼
https://payment-service.internal
```

The request is then forwarded to the target service.

```text
Client
  │
  │ GET /api/payment/123
  ▼
ContractLens Gateway
  │
  │ Resolve Route
  ▼
Payment Service
```

At the same time, the Gateway can publish API traffic or contract-related events asynchronously for analysis:

```text
Request / Response
        │
        ▼
ContractLens Gateway
        │
        ├── Forward Request
        │        │
        │        ▼
        │   Target Service
        │
        └── Publish Event
                 │
                 ▼
              RabbitMQ
```

The Gateway does not perform expensive contract comparison for every request.

Its primary responsibility is to keep API traffic flowing efficiently.

> **Gateway asks: How does traffic flow?**

---

# Contract Analyzer

The Contract Analyzer is responsible for observing and analyzing API contract changes asynchronously.

```text
Gateway Event
      │
      ▼
   RabbitMQ
      │
      ▼
Analyzer Service
      │
      ▼
Contract Snapshot
      │
      ▼
Baseline Detection
      │
      ▼
Contract Comparison
      │
      ▼
Compatibility Analysis
      │
      ▼
Compatibility Plan
```

The Analyzer is responsible for building ContractLens' understanding of API evolution.

Current analysis coverage includes:

- Request Body Changes
- Response Body Changes
- Header Changes
- Contract Snapshot
- Baseline Detection
- Contract Comparison
- API History

## Example

Assume the Payment API previously returned:

```json
{
  "id": 123,
  "amount": 1000,
  "status": "PAID"
}
```

A new version of the API now returns:

```json
{
  "id": 123,
  "amount": "1000",
  "status": "PAID"
}
```

The Analyzer creates or compares contract snapshots:

```text
Previous Snapshot
──────────────────────

amount: Integer


Current Snapshot
──────────────────────

amount: String
```

The Analyzer detects:

```text
CONTRACT CHANGE DETECTED

Field:
amount

Previous Type:
Integer

Current Type:
String
```

The detected change is then passed into compatibility analysis.

The Analyzer answers:

> **What changed?**

This analysis is performed asynchronously so that contract intelligence processing does not unnecessarily increase API request latency.

---

# Compatibility Engine

The Compatibility Engine interprets detected contract changes, determines their impact on existing consumers, and generates a compatibility strategy when possible.

Conceptually:

```text
Previous Contract
       │
       ▼
Current Contract
       │
       ▼
Contract Comparison
       │
       ▼
Compatibility Engine
       │
       ├── Compatible
       │
       ├── Breaking
       │
       └── Transformation Strategy
```

The Compatibility Engine answers questions such as:

- Can existing consumers continue to use the API safely?
- Is the change breaking?
- Can the old contract be preserved?
- Is a runtime transformation possible?

Current compatibility work is focused on:

- Data Type Compatibility

The architecture is designed to expand toward:

- Required Field Compatibility
- JSON Structure Compatibility
- Runtime Payload Transformation
- Runtime Compatibility Protection

## Example: Compatible Change

Imagine an API changes:

```text
amount: Integer
        │
        ▼
amount: Long
```

The Compatibility Engine evaluates the change:

```text
Integer → Long
```

And may determine:

```text
Compatibility Result:

COMPATIBLE
```

No runtime transformation is required.

---

## Example: Breaking Change

Imagine an API changes:

```text
customer: Object
        │
        ▼
customer: String
```

The Compatibility Engine evaluates:

```text
Object → String
```

Result:

```text
Compatibility Result:

BREAKING
```

Existing consumers expecting:

```json
{
  "customer": {
    "id": "C123",
    "name": "Bima"
  }
}
```

may no longer be able to process:

```json
{
  "customer": "C123"
}
```

The Compatibility Engine identifies the impact and can report that no safe transformation strategy is currently available.

---

## Example: Transformable Change

Imagine an old consumer expects:

```json
{
  "amount": 1000
}
```

But the new API returns:

```json
{
  "amount": "1000"
}
```

The Compatibility Engine determines that the change may be transformable:

```text
Previous Type:
Integer

Current Type:
String
        │
        ▼
Transformation Strategy
        │
        ▼
String → Integer
```

A Compatibility Plan can then be generated:

```text
Field:
amount

Transformation:
STRING_TO_INTEGER
```

---

# Compatibility Plan

Compatibility plans are prepared during analysis rather than calculated repeatedly during live API traffic.

```text
Analysis Time
────────────────────────

Compare Contracts
       │
       ▼
Compatibility Engine
       │
       ▼
Generate Compatibility Plan
       │
       ▼
Persist Result
```

For example:

```text
Compatibility Plan

API:
Payment API

Field:
amount

Previous Type:
Integer

Current Type:
String

Strategy:
STRING_TO_INTEGER
```

During runtime, the Gateway does not need to compare contracts again.

```text
Runtime
────────────────────────

Client Request
       │
       ▼
Gateway
       │
       ▼
O(1) Compatibility Plan Lookup
       │
       ▼
Apply Transformation
```

## Runtime Example

The target service returns:

```json
{
  "id": 123,
  "amount": "1000",
  "status": "PAID"
}
```

The Gateway finds the previously generated Compatibility Plan:

```text
amount
STRING_TO_INTEGER
```

The runtime transformation produces:

```json
{
  "id": 123,
  "amount": 1000,
  "status": "PAID"
}
```

The existing consumer continues receiving the contract it expects.

> **Compatibility Engine asks: What does this change mean, and can compatibility be preserved?**

---

# How the Components Work Together

The complete ContractLens flow can be summarized as:

```text
1. Gateway
────────────────────────────

"How does traffic flow?"

Client
   │
   ▼
Gateway
   │
   ▼
Target API


2. Analyzer
────────────────────────────

"What changed?"

API Event
   │
   ▼
Contract Snapshot
   │
   ▼
Compare with Baseline
   │
   ▼
Detected Change


3. Compatibility Engine
────────────────────────────

"What does this change mean?"

Detected Change
   │
   ▼
Compatibility Evaluation
   │
   ├── Compatible
   ├── Breaking
   └── Transformable
          │
          ▼
   Compatibility Plan


4. Gateway Runtime
────────────────────────────

"Apply protection when needed."

API Response
   │
   ▼
Compatibility Plan Lookup
   │
   ▼
Runtime Transformation
   │
   ▼
Client
```

In short:

```text
Gateway
    │
    └── How does traffic flow?

Analyzer
    │
    └── What changed?

Compatibility Engine
    │
    └── What does the change mean,
        and can compatibility be preserved?

Gateway Runtime
    │
    └── Apply the prepared protection.

CLAra
    │
    └── Help the user understand
        what ContractLens discovered.
```

---

# Architecture Philosophy

ContractLens separates operational data, contract intelligence, runtime protection, and conversational interaction.

## Operational Domain

**Source of Truth: PostgreSQL**

Stores:

- Route Configuration
- Token Configuration
- Workspace
- API Registration
- Gateway Configuration

## Contract Intelligence Domain

**Source of Truth: MongoDB**

Stores:

- Contract Snapshot
- Baseline
- Comparison Result
- Compatibility Plan
- API History

MongoDB acts as the **Contract Intelligence Knowledge Base**.

## Runtime Domain

The Gateway is optimized for fast execution.

Key principles:

- Memory-first execution
- Cached compatibility plans
- No runtime contract comparison
- O(1) compatibility plan lookup
- Minimal runtime overhead

---

# CLAra — ContractLens AI Robot Assistant

**CLAra** stands for **ContractLens AI Robot Assistant**.

CLAra is not the ContractLens core engine.

CLAra is the **assistant and conversational interaction layer** that helps users interact with ContractLens more naturally.

```text
                    ContractLens
                         │
      ┌──────────────────┼──────────────────┐
      ▼                  ▼                  ▼
   Gateway            Analyzer        Compatibility
                                           Engine
      │                  │                  │
      └──────────────────┼──────────────────┘
                         │
                         ▼
                    CLAra
                         │
                         ▼
              Conversational Interaction
```

ContractLens remains responsible for:

- API traffic handling
- Contract detection
- Contract comparison
- Compatibility decisions
- Runtime protection
- Authoritative data

CLAra helps users:

- Understand ContractLens concepts
- Retrieve relevant API information
- Understand analysis results
- Navigate platform capabilities
- Interact with ContractLens using natural language

---

# Why CLAra?

Traditional platform interaction may require users to navigate multiple screens:

```text
Dashboard
   │
   ▼
Select Workspace
   │
   ▼
Select API
   │
   ▼
Open Contract History
   │
   ▼
Open Analysis Result
   │
   ▼
Inspect Compatibility
```

CLAra introduces an additional interaction path:

```text
"CLAra, check the latest change on payment API."
```

The user should not necessarily need to know which screen contains the information.

CLAra helps resolve:

```text
What does the user mean?
        │
        ▼
What ContractLens information is relevant?
        │
        ▼
Retrieve authoritative data
        │
        ▼
Present it clearly
```

The traditional UI and conversational interaction can coexist.

CLAra complements the platform; it does not replace deterministic platform capabilities.

---

# CLAra Interaction Architecture

CLAra uses a hybrid AI and deterministic retrieval architecture.

```text
                    User Message
                         │
                         ▼
                 Intent Understanding
                         │
          ┌──────────────┴──────────────┐
          │                             │
          ▼                             ▼
     Known Intent                 Semantic Resolution
          │                             │
          └──────────────┬──────────────┘
                         ▼
                  Context Resolution
                         │
                         ▼
              Determine Required Data
                         │
                         ▼
             ContractLens Retrieval
          ┌──────────────┼──────────────┐
          ▼              ▼              ▼
     PostgreSQL       MongoDB        Services
          │              │              │
          └──────────────┴──────────────┘
                         │
                         ▼
                 Authoritative Result
                         │
                         ▼
                   CLAra Response
```

The central principle is:

> **CLAra understands the user. ContractLens provides the truth.**

AI is not the source of truth.

The AI layer helps interpret the user's request. ContractLens services and databases remain responsible for authoritative data and deterministic analysis.

---

# Current CLAra Capabilities

## Intent Understanding

Current primary intents:

```text
GREETING_USER
GLOSSARY_CONTRACTLENS
REGISTER_API
LOGIN_CONTRACTLENS
ANALYZE_API_CONTRACT
UNKNOWN
```

CLAra is designed to understand semantic variations rather than depending entirely on exact command matching.

Example:

```text
"Daftarin API baru dong"
"Tambahin API"
"Mau register endpoint baru"
```

can resolve to:

```text
REGISTER_API
```

---

## Semantic and Vector Retrieval

Vector embeddings are used for semantic understanding and retrieval.

They can help resolve:

- User intent
- ContractLens terminology
- Relevant knowledge
- Context references
- Similar expressions

Vector retrieval does not replace authoritative operational or contract data.

Live platform data remains grounded in local ContractLens systems.

---

## Context-Aware Interaction

CLAra can use available context to understand what the user is referring to.

For example:

```text
"Check the payment API."
```

followed by:

```text
"Is the latest change safe?"
```

The goal is to resolve the second request against the relevant available context instead of treating every message as completely independent.

---

## Local Data Grounding

Authoritative information is retrieved from:

- PostgreSQL
- MongoDB
- ContractLens Services
- Contract Analysis Results
- Compatibility Results
- API History

```text
AI understands the request
          │
          ▼
ContractLens retrieves the facts
          │
          ▼
CLAra presents the result
```

---

# AI Model Architecture

The AI model is an interchangeable implementation behind CLAra's understanding layer.

```text
                     CLAra
                       │
                       ▼
             AI Understanding Layer
                       │
          ┌────────────┴────────────┐
          │                         │
          ▼                         ▼
     Cloud Model               Local Model
      Provider                  Provider
          │                         │
          └────────────┬────────────┘
                       ▼
               Structured Meaning
                       │
                       ▼
          ContractLens Retrieval Layer
```

The model helps CLAra understand the user's natural-language request.

ContractLens remains responsible for:

- Data retrieval
- Business logic
- Authorization
- Contract analysis
- Compatibility decisions
- Source-of-truth management

The AI provider can therefore evolve without changing the core ContractLens architecture.

---

# Engineering Principles

## Functional Principles

- API First
- Contract First
- Backward Compatibility
- Rule-based Transformation
- Asynchronous Contract Analysis
- Runtime Compatibility Protection
- Natural Language Interaction
- Locally Grounded Data Retrieval
- AI-Assisted Understanding

## Non-Functional Principles

- Performance First
- Reactive Processing
- Memory-first Execution
- Minimal Runtime Overhead
- Reliability
- Scalability by Design
- High Observability
- Testability
- Privacy by Architecture
- Self-Hosted Deployment

---

# Self-Hosted and Privacy Philosophy

ContractLens is designed with self-hosting as a primary deployment model.

Customer data should remain within the customer's environment whenever possible.

This is particularly important for:

- API definitions
- Request and response structures
- Headers
- Authentication-related configuration
- Contract snapshots
- API history
- Compatibility results
- Internal service topology

Cloud or local AI models may be used behind CLAra depending on deployment requirements.

The authoritative ContractLens platform remains locally grounded.

The long-term direction includes support for private AI deployment within customer infrastructure.

---

# Features

## API Gateway

- Spring WebFlux
- Reverse Proxy
- Dynamic Route Resolution
- PostgreSQL Route Management
- Reactive Request Processing
- Reactive Response Processing
- Reliable RabbitMQ Publishing

## Contract Intelligence

- Contract Snapshot
- Baseline Detection
- Contract Comparison
- Request Body Change Detection
- Response Body Change Detection
- Header Change Detection
- API History
- Compatibility Plan Generation

## Compatibility Engine

- Data Type Compatibility
- Compatibility Impact Evaluation
- Compatibility Strategy Generation
- Planned Required Field Compatibility
- Planned JSON Structure Compatibility
- Planned Runtime Payload Transformation
- Planned Runtime Compatibility Protection
- Caffeine Local Cache

## CLAra Assistant

- Intent Understanding
- Vector Embeddings
- Semantic Retrieval
- Context-Aware Interaction
- Local Data Grounding
- Conversational ContractLens Interaction

---

# Roadmap

## Sprint 1 ✅

### Foundation

- Project Initialization
- Multi Module Architecture
- Docker
- Common Library

---

## Sprint 2 ✅

### API Gateway

- Reverse Proxy
- Dynamic Route Resolution
- PostgreSQL
- Token Management
- Flyway

---

## Sprint 3 ✅

### Reliable Event Processing

- RabbitMQ
- Retry
- Outbox Pattern
- Async Processing

---

## Sprint 4 ✅

### Contract Intelligence

- Snapshot
- Baseline
- Comparison
- History
- MongoDB

---

## Sprint 5 ✅

### Gateway Enhancement

- Dynamic Route Resolution
- Read-through Cache
- Route Management
- Cache Rebuild

---

## Sprint 6 🚧

### Goal

Build the Compatibility Engine while migrating the Gateway to Spring WebFlux.

| Story | Status |
|---|---|
| Story 6.0 — Gateway Refactoring | 🚧 |
| Story 6.1 — Compatibility Engine Foundation | 🚧 |
| Story 6.2 — Data Type Compatibility | 🚧 |
| Story 6.3 — Required Field Compatibility | ⬜ |
| Story 6.4 — JSON Structure Compatibility | ⬜ |
| Story 6.5 — Compatibility Cache (Caffeine) | ⬜ |
| Story 6.6 — Compatibility Runtime | ⬜ |
| Story 6.7 — Compatibility Validation | ⬜ |
| Story 6.8 — Performance Optimization | ⬜ |
| Story 6.9 — Sprint Validation | ⬜ |

### Sprint 6 Technical Decisions

- Spring WebFlux for Gateway runtime
- MongoDB as Contract Intelligence Source of Truth
- PostgreSQL as Operational Source of Truth
- Caffeine as Local Compatibility Cache
- Compatibility Plans loaded at Gateway startup
- No runtime contract comparison
- Memory-first execution
- O(1) CompatibilityPlan lookup

---

## Sprint 7

### Interactive Platform

- Interactive Dashboard
- Improved platform interaction
- API and contract visibility

---

## Sprint 8

### CLAra and AI-Assisted Interaction

- CLAra integration
- Intent understanding
- Semantic and vector retrieval
- Context-aware interaction
- Local data grounding
- Conversational ContractLens interaction
- Response quality refinement

Current CLAra priority:

```text
Understand accurately
        ↓
Retrieve the truth
        ↓
Explain clearly
```

---

## Sprint 9

### Enterprise and Private AI

- Enterprise Features
- Self-hosted AI deployment options
- Private model integration
- Deployment-specific model selection
- AI privacy controls
- Advanced observability

---

# Sprint Validation 🧪

Validation includes:

- Unit Testing
- Integration Testing
- Functional Testing
- Regression Testing
- JVM Memory Profiling
- Caffeine Benchmark
- Garbage Collection Analysis
- Throughput Benchmark
- Latency Benchmark
- Load Testing
- Stress Testing

AI interaction validation will progressively include:

- Intent Accuracy
- Context Resolution Accuracy
- Retrieval Correctness
- Structured Interpretation Validity
- Model Latency
- Failure Rate
- Informal Language Understanding

Target gateway performance:

- Average Overhead < 20 ms
- P95 < 50 ms
- P99 < 100 ms

---

# Current Status

## Completed

### Gateway and Contract Intelligence

- API Gateway
- Dynamic Routing
- RabbitMQ
- Contract Snapshot
- Baseline Detection
- Contract Comparison
- API History
- Request Body Change Detection
- Response Body Change Detection
- Header Change Detection

### CLAra Foundation

- Intent-based Understanding
- Vector Embeddings
- Semantic Retrieval
- Context-Aware Interaction
- Local Data Retrieval and Grounding
- Conversational Interaction Foundation

## In Progress

- Spring WebFlux Migration
- Compatibility Engine Foundation
- Data Type Compatibility
- CLAra Interaction and Response Refinement

## Planned

### ContractLens Core

- Caffeine Runtime Cache
- Runtime Compatibility
- Required Field Compatibility
- JSON Structure Compatibility
- Performance Validation

### CLAra

- Expanded Intent Coverage
- Improved Context Resolution
- Structured Interpretation
- Model Benchmarking
- Local AI Model Experimentation
- AI Evaluation and Observability

---

# Future Vision

ContractLens aims to become a complete **API Contract Intelligence Platform** capable of:

- Observing API traffic and contract evolution
- Detecting meaningful API changes
- Preserving backward compatibility
- Executing runtime transformations
- Providing API and contract history
- Supporting conversational interaction through CLAra
- Explaining ContractLens analysis in natural language
- Supporting private and self-hosted AI deployments
- Recommending compatibility strategies
- Providing enterprise-grade observability

CLAra will evolve alongside the platform:

```text
Understand
    ↓
Explain
    ↓
Assist
    ↓
Recommend
    ↓
Act
```

But the foundation remains the same:

> **ContractLens is the platform. CLAra is the assistant.**

---

# Closing

> **Observe. Detect. Protect.**

ContractLens combines API Gateway capabilities, asynchronous contract analysis, compatibility protection, and conversational assistance to help engineering teams deploy APIs with confidence.
