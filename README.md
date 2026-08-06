ContractLens

«Observe. Detect. Protect.»

ContractLens is an intelligent API Gateway and Contract Intelligence Platform that helps engineering teams detect, analyze, and prevent API breaking changes before they impact consumers.

Instead of relying on manual API reviews, ContractLens automatically captures API traffic, analyzes contracts, detects differences, prepares compatibility rules, and preserves backward compatibility with minimal runtime overhead.

---

Vision

«Give developers confidence when deploying APIs.»

ContractLens allows backend services to evolve safely while keeping existing consumers protected from unexpected contract changes.

---

Engineering Principles

ContractLens is designed using enterprise-grade engineering principles.

Functional Principles

- API First
- Contract First
- Backward Compatibility
- Rule-based Transformation
- Asynchronous Contract Analysis
- Runtime Compatibility Protection

Non-Functional Principles

- Performance First
- Memory-first Execution
- Minimal Runtime Overhead
- Reliability
- Scalability by Design
- High Observability
- Testability

---

Problem

Modern APIs evolve rapidly.

Even a small API contract change can introduce production issues.

Examples:

- Integer → String
- Required field removed
- Field renamed
- Response structure changed
- New mandatory field
- Header contract changed

Without automated verification, developers must manually review every API response, making deployments slower and more error-prone.

---

Solution

ContractLens combines an API Gateway with a Contract Intelligence Engine.

Every API request automatically flows through ContractLens.

Client
    │
    ▼
ContractLens Gateway
    │
    ▼
Target Service

Meanwhile, ContractLens performs asynchronous contract analysis.

Request / Response
        │
        ▼
RabbitMQ
        │
        ▼
Analyzer
        │
        ▼
Generate Contract Snapshot
        │
        ▼
Baseline Detection
        │
        ▼
Contract Comparison
        │
        ▼
Compatibility Rule Generation
        │
        ▼
Store History & Rules

At runtime, the Gateway does not perform contract comparison.

Instead, it executes pre-generated compatibility rules.

Gateway
    │
    ▼
Load Compatibility Rule (Memory Cache)
    │
    ▼
Runtime Transformation
    │
    ▼
Return Response

---

Features

API Gateway

- Reverse Proxy
- Dynamic Route Resolution
- PostgreSQL Route Management
- Redis Cache Layer
- Token-based Endpoint Resolution
- Request Capture
- Response Capture
- Reliable RabbitMQ Publishing

---

Contract Snapshot

Every request and response passing through ContractLens is transformed into a normalized Contract Snapshot.

Each snapshot contains:

- Request Header Contract
- Request Body Contract
- Response Header Contract
- Response Body Contract
- Field Type Information
- Nested Object Structure

---

Contract Comparison

Automatically detects:

- Added Fields
- Removed Fields
- Type Changes
- Required Field Changes
- Nested Object Changes
- Array Structure Changes
- Potential Breaking Changes

---

Compatibility Engine

Instead of simply reporting breaking changes, ContractLens attempts to preserve backward compatibility.

Supported compatibility includes:

- Type Compatibility
- Field Compatibility
- Structure Compatibility
- Collection Compatibility
- Enum Compatibility
- Default Value Compatibility
- Complex Object Compatibility
- Runtime Payload Transformation

---

Architecture

                    Client
                       │
                       ▼
              ContractLens Gateway
                       │
             Runtime Transformation
                       │
                       ▼
                 Target Service

                       │
                       ▼

        ───────── Asynchronous Pipeline ─────────

               RabbitMQ Event
                       │
                       ▼
             ContractLens Analyzer
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
       Compatibility Rule Generation
                       │
                       ▼
          MongoDB + PostgreSQL

---

Roadmap

Sprint 1 ✅

Foundation

- Project Initialization
- Multi Module Architecture
- Common Library
- Shared DTO
- Docker Environment

---

Sprint 2 ✅

API Gateway

- Reverse Proxy
- Dynamic Target Routing
- PostgreSQL Configuration
- Token Management
- Dynamic Route Resolution
- Redis Cache Layer
- Gateway Route Management
- Flyway Migration

---

Sprint 3 ✅

Reliable Event Processing

- RabbitMQ Integration
- Reliable Event Publishing
- Retry Mechanism
- Outbox Pattern
- Asynchronous Processing

---

Sprint 4 ✅

Contract Intelligence

- Contract Snapshot
- Contract Comparison
- Baseline Detection
- API History
- MongoDB Storage

---

Sprint 5 ✅

Dynamic Route Resolution

- PostgreSQL as Source of Truth
- Redis Cache Layer
- Read-Through Cache
- Write Synchronization
- Automatic Cache Rebuild

---

Sprint 6 🚧

Compatibility Engine

The Compatibility Engine evaluates every detected contract change and attempts to preserve backward compatibility before declaring a breaking change.

Story Backlog

— Type Compatibility
— Field Compatibility
— Structure Compatibility
— Collection Compatibility
— Enum Compatibility
— Default Value Compatibility
— Complex Object Compatibility
— Runtime Compatibility Transformation
- ⬜ Story 6.9 — Compatibility Report

Performance Requirements

Every story must satisfy both Functional and Non-Functional Acceptance Criteria.

Functional

- Feature works correctly
- Unit Test completed
- Integration Test completed

Non-Functional

- No runtime contract comparison
- No RabbitMQ communication
- No database lookup during transformation (except cache miss)
- Memory-first execution
- Single-pass transformation whenever possible
- Low memory allocation
- Minimal additional latency

---

Sprint 6.5 🧪

Validation & Performance Testing

This sprint acts as the quality gate before introducing Dashboard and AI features.

Scope

Functional Validation

- End-to-End Testing
- Integration Testing
- Regression Testing
- Cross Story Validation

Data Validation

- Small Dataset
- Medium Dataset
- Large Dataset
- Edge Cases
- Compatibility Accuracy

Performance Validation

- Runtime Benchmark
- Memory Profiling
- CPU Profiling
- Latency Measurement
- Load Testing
- Stress Testing

Testing Strategy

Every feature should be validated through:

Unit Test
      │
      ▼
Functional Test
      │
      ▼
Integration Test
      │
      ▼
Regression Test
      │
      ▼
Performance Test
      │
      ▼
Load Test
      │
      ▼
Stress Test
      │
      ▼
Large Dataset Validation

---

Sprint 7

Interactive Dashboard

- Contract Diff Viewer
- API Timeline
- Search
- Filtering
- Baseline Approval

---

Sprint 8

AI Features

- AI Contract Explanation
- AI Compatibility Suggestion
- AI Rule Recommendation
- Natural Language Query

---

Sprint 9

Enterprise Features

- Notification Center
- Slack Integration
- Email Notification
- Webhook Integration
- Multi Workspace
- API Version Management
- Role Based Access Control

---

Current Status

Completed

- API Gateway
- Dynamic Routing
- Redis Cache
- RabbitMQ
- Contract Snapshot
- Contract Comparison
- Baseline Detection
- API History

In Progress

- Compatibility Engine

Planned Validation

- Functional Validation
- Performance Benchmark
- Compatibility Validation
- Large Dataset Validation
- Load Testing
- Stress Testing

---

Future Vision

ContractLens aims to evolve from an API Gateway into a complete API Contract Intelligence Platform.

Future capabilities include:

- Interactive Dashboard
- Runtime Compatibility Engine
- AI-assisted Contract Analysis
- AI-generated Compatibility Rules
- Performance Validation Suite
- Notification Center
- Enterprise Workspace Management

---

Closing

«Observe. Detect. Protect.»

ContractLens gives engineering teams confidence to evolve backend APIs safely by combining intelligent contract analysis, runtime compatibility protection, and enterprise-grade engineering principles.