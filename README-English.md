# ContractLens

> **Observe. Detect. Protect.**

ContractLens is a **self-hosted API Contract Intelligence Platform**
designed to help engineering teams observe, understand, analyze, and safely
evolve their APIs without exposing sensitive API traffic, contracts, or
internal data outside their own infrastructure.

At the center of the platform is **CLAra — ContractLens AI Robot Assistant**.

CLAra provides a conversational interaction layer for the ContractLens
ecosystem, while the Gateway, Analyzer, Compatibility Engine, and data
services operate inside the customer's environment.

> **Observe. Detect. Protect.**

---

# Vision

> **Give developers confidence when evolving APIs — without giving up control of their data.**

Modern APIs constantly evolve.

Fields may be added, removed, renamed, or changed. Headers may evolve.
Request and response structures may change. Existing consumers may depend
on contracts that were previously stable.

A seemingly small API change can silently introduce a breaking change.

ContractLens helps engineering teams observe and understand these changes,
analyze their impact, and prepare compatibility strategies while keeping
API intelligence inside their own environment.

The long-term vision is:

> **Give every engineering team an intelligent API guardian that lives inside their infrastructure.**

---

# CLAra

> **ContractLens AI Robot Assistant**

CLAra is the conversational interaction layer of ContractLens.

CLAra is not intended to be a generic chatbot attached to a dashboard.

Its purpose is to help users understand and operate the ContractLens
ecosystem through natural interaction.

Instead of navigating through multiple configuration pages, users should
be able to express what they want to accomplish.

For example:

> **"CLAra, register my payment API."**

> **"Analyze the contract for this API."**

> **"What changed since the last version?"**

> **"Why is this change breaking?"**

> **"Show me the compatibility result."**

The intended interaction model is:

```text
User Intent
     │
     ▼
CLAra
     │
     ├── Understand
     ├── Retrieve Context
     ├── Collect Information
     ├── Execute Tools
     └── Explain Results
             │
             ▼
      ContractLens Platform
```

CLAra is currently evolving from an **intent-based assistant** into a
conversational API operations assistant.

---

# Current CLAra Architecture

CLAra currently uses semantic understanding through vector embeddings to
help identify user intent.

The current flow is:

```text
User Message
     │
     ▼
Vector Embedding
     │
     ▼
Similarity Matching
     │
     ▼
Intent Resolution
     │
     ▼
Intent Handler
     │
     ▼
Response / Action Flow
```

Current intents include:

```text
GREETING_USER
GLOSSARY_CONTRACTLENS
REGISTER_API
LOGIN_CONTRACTLENS
ANALYZE_API_CONTRACT
UNKNOWN
```

Examples:

```text
"Hi CLAra"
        │
        ▼
GREETING_USER
```

```text
"What is a compatibility plan?"
        │
        ▼
GLOSSARY_CONTRACTLENS
```

```text
"Register a new API"
        │
        ▼
REGISTER_API
```

```text
"Analyze this API"
        │
        ▼
ANALYZE_API_CONTRACT
```

This architecture provides a lightweight foundation for understanding
common ContractLens operations without requiring complex AI reasoning for
every interaction.

---

# CLAra Evolution

CLAra is designed to evolve incrementally.

The goal is not to immediately depend on a large language model for every
conversation.

Instead, CLAra will gradually evolve through several stages.

## Level 1 — Intent Assistant

**Current Stage**

```text
User Message
     │
     ▼
Embedding
     │
     ▼
Intent Detection
     │
     ▼
Intent Handler
```

Current capabilities:

- Greeting
- ContractLens glossary
- Semantic intent recognition
- Intent routing
- Basic intent handling
- Unknown intent detection

---

## Level 2 — Conversational Operator

The next stage introduces conversation state and parameter collection.

```text
User
 │
 │ "CLAra, register an API."
 ▼
REGISTER_API
 │
 ├── API Name?
 │
 ▼
Conversation State
 │
 ├── Base URL?
 │
 ▼
Conversation State
 │
 ├── Authentication?
 │
 ▼
Confirmation
 │
 ▼
Execute Registration
```

CLAra begins to understand that an operation may require multiple pieces
of information.

Instead of returning a static response, CLAra can guide the user through
a complete operation.

Capabilities:

- Conversation state
- Parameter extraction
- Missing parameter detection
- Multi-step interaction
- Context persistence
- Confirmation flow
- Action execution

---

## Level 3 — Tool-Using Assistant

CLAra begins interacting directly with ContractLens capabilities.

```text
User Goal
     │
     ▼
Understand Intent
     │
     ▼
Select Tool
     │
     ├── Register API
     ├── Get API
     ├── Analyze Contract
     ├── Get History
     └── Get Compatibility
            │
            ▼
         Execute
            │
            ▼
         Result
```

CLAra does not replace ContractLens business logic.

ContractLens remains responsible for deterministic operations.

CLAra becomes responsible for understanding user intent and orchestrating
available capabilities.

---

## Level 4 — Local AI Reasoning

Complex interactions may require reasoning beyond deterministic intent
matching.

CLAra may currently use cloud-based LLM capabilities where required, but
the intended architecture is to reduce this dependency and support AI
reasoning inside the customer's own environment.

```text
Complex User Request
        │
        ▼
      CLAra
        │
        ├── Embedding
        ├── Intent
        ├── Context
        │
        ▼
   Local AI Runtime
        │
        ▼
     Reasoning
        │
        ▼
  ContractLens Tools
```

The goal is to support private AI inference without requiring API data or
contract intelligence to be sent to external AI providers.

---

## Level 5 — Bounded Agent

The long-term architecture allows CLAra to operate as a bounded agent
inside the ContractLens ecosystem.

```text
User Goal
    │
    ▼
Understand Context
    │
    ▼
What information is required?
    │
    ▼
Select Tool
    │
    ▼
Execute
    │
    ▼
Observe Result
    │
    ├── More work required
    │        │
    │        └── Reason Again
    │
    └── Goal Complete
             │
             ▼
          Respond
```

CLAra should operate through explicitly defined ContractLens tools,
permissions, and execution boundaries.

The goal is not unrestricted autonomy.

The goal is:

> **Controlled intelligence capable of understanding goals and operating the ContractLens ecosystem safely.**

---

# Conversational API Operations

ContractLens follows a **chat-first interaction model**.

The goal is simple:

> **Don't make the user navigate to the feature.**
>
> **Make the feature come to the conversation.**

Traditional interaction:

```text
Dashboard
  │
  ▼
API Menu
  │
  ▼
Select API
  │
  ▼
Configuration
  │
  ▼
Save
```

ContractLens:

```text
User
 │
 │ "CLAra, register my payment API."
 ▼
CLAra
 │
 ▼
Conversation
 │
 ▼
Contextual UI
 │
 ▼
Confirmation
 │
 ▼
Action
```

Chat provides the **intent**.

Contextual UI provides **visualization and confirmation**.

ContractLens performs the **deterministic operation**.

The intended interaction model is:

> **Conversation → Context → Action**

---

# Privacy by Architecture

API traffic may contain sensitive information.

This may include:

- Authentication tokens
- Customer information
- Payment information
- Internal business data
- Proprietary API structures
- Internal endpoints
- Request payloads
- Response payloads
- Headers
- Contract snapshots
- API history

ContractLens is designed around a simple principle:

> **Sensitive API intelligence should not need to leave the customer's infrastructure.**

The platform is designed to run inside the customer's environment.

```text
┌─────────────────────────────────────────────────────┐
│               CUSTOMER ENVIRONMENT                  │
│                                                     │
│                    👤 User                          │
│                       │                             │
│                       ▼                             │
│                    🤖 CLAra                         │
│                       │                             │
│              Local AI Runtime                       │
│                       │                             │
│             ┌─────────┼─────────┐                   │
│             ▼         ▼         ▼                   │
│          Gateway   Analyzer   Platform              │
│             │         │                             │
│             └─────────┼─────────┘                   │
│                       ▼                             │
│                  API Ecosystem                      │
│                                                     │
│        PostgreSQL · MongoDB · RabbitMQ              │
│                                                     │
└─────────────────────────────────────────────────────┘
```

The architecture is designed so that:

- API traffic remains internal
- Request data remains internal
- Response data remains internal
- Headers remain internal
- Contract snapshots remain internal
- API history remains internal
- Contract intelligence remains internal
- AI inference can run internally

Security is therefore supported by architecture.

> **Your data does not need to leave your environment.**

---

# Self-Hosted by Design

ContractLens is designed to run inside customer infrastructure.

Potential deployment environments include:

- Docker
- Docker Compose
- Virtual Machines
- Private Servers
- Kubernetes
- Private Cloud
- On-Premise Infrastructure

A typical deployment may look like:

```text
Customer Infrastructure
│
├── ContractLens Platform
│
├── CLAra Runtime
│   ├── Embedding Engine
│   ├── Intent Resolution
│   └── Local AI Runtime
│
├── ContractLens Gateway
│
├── ContractLens Analyzer
│
├── PostgreSQL
│
├── MongoDB
│
└── RabbitMQ
```

The customer owns the infrastructure.

ContractLens provides the platform.

CLAra operates within that environment.

---

# AI Efficiency Philosophy

CLAra should not require AI inference for every operation.

The preferred execution strategy is:

```text
                    User Request
                         │
                         ▼
                  Rule / Embedding
                         │
                High Confidence?
                  │           │
                 Yes          No
                  │           │
                  ▼           ▼
           Deterministic    AI Reasoning
             Execution       if Required
                  │           │
                  └─────┬─────┘
                        ▼
                 ContractLens Tool
```

Examples of operations that may not require complex AI reasoning:

```text
"Show my APIs"
```

```text
"Register an API"
```

```text
"Analyze API payment"
```

These operations can be routed through known intents and deterministic
tools.

AI reasoning becomes useful for questions such as:

```text
"Why is this change dangerous for existing consumers?"
```

or:

```text
"What would be the safest compatibility strategy?"
```

The design principle is:

> **Use deterministic systems when deterministic systems are sufficient.**

> **Use AI when reasoning adds meaningful value.**

---

# The ContractLens Ecosystem

ContractLens consists of multiple components working together.

```text
                      👤 User
                         │
                         ▼
                      🤖 CLAra
                 Intent & Interaction
                         │
                         ▼
                 ContractLens Platform
                         │
            ┌────────────┼────────────┐
            ▼            ▼            ▼
         Gateway      Analyzer      Data Layer
            │            │
            └────────────┼────────────┘
                         ▼
                    API Ecosystem
```

## CLAra — The Brain

CLAra is responsible for interaction and orchestration.

Capabilities include:

- Intent understanding
- Semantic similarity through embeddings
- Conversation flow
- Context retrieval
- Parameter collection
- Tool orchestration
- Contract explanation
- API operation assistance

---

## Analyzer — The Eyes

The Analyzer is responsible for observing and understanding API contracts
and changes.

Current capabilities include:

- Request contract observation
- Response contract observation
- Header observation
- Contract snapshot generation
- Baseline detection
- Contract comparison
- API history
- Change detection

Future capabilities include:

- Advanced compatibility analysis
- Required field compatibility
- JSON structure compatibility
- AI-assisted explanation

---

## Gateway — The Hands

The Gateway operates on the runtime path.

Responsible for:

- Reverse Proxy
- Dynamic Route Resolution
- Reactive Request Processing
- Reactive Response Processing
- Contract Observation
- Runtime Compatibility Protection
- Runtime Payload Transformation

---

## Compatibility Engine — The Protector

The Compatibility Engine determines whether detected API changes can be
handled safely at runtime.

The current compatibility implementation is focused on:

- Data type compatibility

Future capabilities include:

- Required field compatibility
- JSON structure compatibility
- Compatibility plan generation
- Runtime transformation
- Compatibility validation

---

# Engineering Principles

## Functional Principles

- API First
- Contract First
- Backward Compatibility
- Conversational API Operations
- Rule-based Transformation
- Asynchronous Contract Analysis
- Runtime Compatibility Protection
- Semantic Intent Understanding
- Local AI
- Privacy by Architecture
- Self-Hosted Deployment

## Non-Functional Principles

- Performance First
- Reactive Processing
- Memory-first Execution
- Minimal Runtime Overhead
- Reliability
- Scalability by Design
- High Observability
- Testability
- Deployment Flexibility
- AI Cost Efficiency
- Data Privacy

---

# Architecture Philosophy

ContractLens separates operational data from contract intelligence while
keeping both inside the customer's environment.

## Operational Domain

**Source of Truth:** PostgreSQL

Stores:

- Route Configuration
- Token Configuration
- Workspace
- API Registration
- Gateway Configuration
- Operational Configuration

---

## Contract Intelligence Domain

**Source of Truth:** MongoDB

Stores:

- Contract Snapshot
- Baseline
- Comparison Result
- Compatibility Plan
- API History
- Future AI Insights

MongoDB acts as the internal **Contract Intelligence Knowledge Base**.

---

## Event Processing

**Message Broker:** RabbitMQ

Responsible for:

- Reliable Event Processing
- Asynchronous Analysis
- Retry
- Outbox Processing
- Decoupled Contract Intelligence

---

# Runtime Architecture

```text
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

Contract analysis is performed asynchronously.

The Gateway runtime is designed to avoid expensive contract comparison for
every request.

Compatibility decisions are prepared in advance and optimized for runtime
execution.

---

# Features

## API Gateway

- Spring WebFlux
- Reverse Proxy
- Dynamic Route Resolution
- PostgreSQL Route Management
- Reactive Request Processing
- Reactive Response Processing
- Contract Observation
- Reliable RabbitMQ Publishing

## Contract Intelligence

- Request Contract Observation
- Response Contract Observation
- Header Observation
- Contract Snapshot
- Baseline Detection
- Contract Comparison
- Change Detection
- API History

## Compatibility Engine

### Current

- Data Type Compatibility

### Planned

- Required Field Compatibility
- JSON Structure Compatibility
- Compatibility Plan Generation
- Compatibility Cache
- Runtime Transformation
- Compatibility Validation

## CLAra

### Current

- Greeting
- ContractLens Glossary
- Vector Embedding
- Semantic Intent Detection
- Intent Routing
- Basic Intent Handling
- Unknown Intent Handling

Current intents:

```text
GREETING_USER
GLOSSARY_CONTRACTLENS
REGISTER_API
LOGIN_CONTRACTLENS
ANALYZE_API_CONTRACT
UNKNOWN
```

### Next

- Conversation State
- Parameter Collection
- Context Persistence
- Register API Flow
- Analyze API Flow
- Tool Execution
- Contextual UI Responses

### Future

- Local LLM Integration
- Private AI Runtime
- Tool Selection
- Contract Explanation
- Multi-step Reasoning
- Bounded Agent Loop

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

- Contract Snapshot
- Baseline Detection
- Contract Comparison
- API History
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

### Compatibility Engine

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

# Sprint Validation

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

Current performance targets:

- Average Overhead < 20 ms
- P95 < 50 ms
- P99 < 100 ms

---

# Next Phase — CLAra Conversational Operations

## Intent Assistant

- Improve semantic intent detection
- Improve intent confidence handling
- Expand ContractLens glossary
- Improve UNKNOWN handling

## Conversational Operator

- Conversation State
- Parameter Collection
- Missing Parameter Detection
- Multi-step Interaction
- Confirmation Flow
- API Registration Flow
- Contract Analysis Flow

## Tool-Using CLAra

- ContractLens Tool Abstraction
- API Registration Tool
- Contract Analysis Tool
- API Query Tool
- Contract History Tool
- Compatibility Query Tool

## Local AI

- AI Provider Abstraction
- Local Model Runtime
- Private Model Integration
- Cloud LLM Dependency Reduction
- Local Contract Explanation
- Local Reasoning

## Bounded Agent

- Goal Understanding
- Context Evaluation
- Tool Selection
- Action Execution
- Result Observation
- Multi-step Operation
- Permission Boundaries

---

# Current Status

## Completed

- API Gateway
- Dynamic Routing
- RabbitMQ
- Contract Snapshot
- Baseline Detection
- Contract Comparison
- API History
- Request Contract Observation
- Response Contract Observation
- Header Observation
- Vector Embedding
- Semantic Intent Detection
- Basic CLAra Intent Handling

## In Progress

- Spring WebFlux Migration
- Compatibility Engine Foundation
- Data Type Compatibility
- CLAra Intent Improvement

## Next Focus

- Required Field Compatibility
- JSON Structure Compatibility
- Compatibility Cache
- Runtime Compatibility
- Performance Validation
- CLAra Conversation State
- Register API Conversation Flow
- Analyze API Conversation Flow
- ContractLens Tool Layer
- Local AI Runtime

---

# Future Vision

ContractLens aims to become a complete **self-hosted API Contract
Intelligence Platform** where engineering teams can interact with their API
ecosystem through CLAra.

The platform will enable teams to:

- Observe request contracts
- Observe response contracts
- Observe API headers
- Detect API changes
- Preserve backward compatibility
- Execute runtime transformations
- Detect breaking changes
- Analyze compatibility
- Explain contract changes
- Register and manage APIs through conversation
- Query API history
- Control supported Gateway operations
- Run AI inside private infrastructure
- Keep API intelligence inside the customer environment

The goal is not to replace engineering judgment.

The goal is to provide a better way to understand and operate API
ecosystems.

---

# Closing

> **ContractLens provides the infrastructure.**
>
> **CLAra makes it conversational.**

```text
CLAra                 → The Brain
Vector Embeddings     → Semantic Understanding
Analyzer              → The Eyes
Gateway               → The Hands
Compatibility Engine  → The Protector
ContractLens          → The Ecosystem
```

> **Observe. Detect. Protect.**

> **Observe. Detect. Protect.**
