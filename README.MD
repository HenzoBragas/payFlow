

## 🧱 Modelo de Dados (Entidades)

| Entidade | Responsabilidade |
|---|---|
| **User** | Representa o cliente do sistema |
| **PaymentMethod** | Formas de pagamento cadastradas |
| **Invoice** | Cobrança que deve ser paga |
| **Transaction** | Registro de pagamento de uma Invoice |
| **Subscription** | Cobranças recorrentes |
| **WebhookEvent** | Armazena eventos enviados pelo gateway |
| **Refund** | Registro de reembolso |

---

## 🔷 Diagrama Entidade-Relacionamento (DER)

```mermaid
erDiagram

    USER ||--o{ PAYMENT_METHOD : has
    USER ||--o{ INVOICE : generates
    USER ||--o{ SUBSCRIPTION : subscribes

    INVOICE ||--o{ TRANSACTION : has
    INVOICE ||--o{ WEBHOOK_EVENT : notifies
    TRANSACTION ||--o| REFUND : mayHave

    USER {
        UUID id
        string name
        string email
    }

    PAYMENT_METHOD {
        UUID id
        enum type
        string externalReference
    }

    INVOICE {
        UUID id
        decimal amount
        string description
        enum status
        datetime createdAt
        datetime updatedAt
    }

    TRANSACTION {
        UUID id
        string gatewayTransactionId
        decimal amount
        enum status
        datetime timestamp
    }

    SUBSCRIPTION {
        UUID id
        string planName
        decimal price
        datetime startDate
        datetime nextBillingDate
        enum status
    }

    WEBHOOK_EVENT {
        UUID id
        string eventType
        text rawPayload
        datetime receivedAt
    }

    REFUND {
        UUID id
        decimal value
        datetime refundedAt
    }
