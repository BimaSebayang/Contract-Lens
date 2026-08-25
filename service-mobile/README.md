features/
└── chat/
│
├── conversation.screen.tsx
├── conversation.script.ts
├── conversation.style.ts
│
├── components/
│   │
│   ├── conversation-header.tsx
│   ├── conversation-list.tsx
│   │
│   ├── message/
│   │   ├── conversation-date.tsx
│   │   ├── initial-message.tsx
│   │   ├── user-message.tsx
│   │   ├── clara-message.tsx
│   │   └── clara-image.ts
│   │
│   ├── input/
│   │   └── conversation-input.tsx
│   │
│   ├── response/
│   │   ├── message-feedback.tsx
│   │   └── clara-loading.tsx
│   │
│   └── interaction/
│       └── interaction-renderer.tsx
│
├── interactions/
│   ├── greeting/
│   │   ├── greeting.interaction.tsx
│   │   └── greeting.script.ts
│   │
│   ├── register-api/
│   │   ├── register-api.interaction.tsx
│   │   └── register-api.script.ts
│   │
│   ├── analyze-contract/
│   │   ├── analyze-contract.interaction.tsx
│   │   └── analyze-contract.script.ts
│   │
│   ├── login/
│   │   ├── login.interaction.tsx
│   │   └── login.script.ts
│   │
│   └── glossary/
│       ├── glossary.interaction.tsx
│       └── glossary.script.ts
│
└── runtime/
├── intent-resolver.ts
├── interaction-resolver.ts
└── conversation-state.ts