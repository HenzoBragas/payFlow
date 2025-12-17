# PayFlow

Sistema de gerenciamento de **assinaturas, faturamento e pagamentos simulados**, desenvolvido em **Java com Spring Boot**, com **processamento assíncrono via RabbitMQ**.

O projeto foi criado com foco em **boas práticas de arquitetura backend**, **Programação Orientada a Objetos (POO)**, **mensageria**, **testes unitários** e **simulação de um fluxo real de pagamento**, sendo ideal para estudo e portfólio.

---

## 🎯 Objetivo do Projeto

Demonstrar, na prática:

* Arquitetura backend bem estruturada
* Uso de mensageria com RabbitMQ
* Processamento assíncrono de eventos
* Boas práticas com Spring Boot
* Testes unitários com Mockito
* Simulação realista de um fluxo de pagamentos

🚀 **Projeto ideal para estudo, portfólio e avaliação de nível backend Java.**

---

## 🏗️ Tecnologias Utilizadas

### Backend

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate

### Banco de Dados

* PostgreSQL
* Flyway Migrations

### Mensageria

* RabbitMQ

### Testes

* JUnit
* Mockito

### Outros

* MapStruct (mapeamento DTO)
* Lombok
* Docker
* Git & GitHub

---

## 📌 Descrição do Projeto

O **PayFlow** é um sistema de gestão de assinaturas e faturamento que permite:

* Cadastro de usuários e planos de assinatura
* Criação de assinaturas vinculadas a usuários e planos
* Geração automática de faturas (**Billing**) associadas às assinaturas
* Simulação de pagamentos via endpoint REST
* Validação de pagamentos através de **webhook**
* Processamento assíncrono de eventos com **RabbitMQ**
* Atualização automática do status das faturas
* Testes unitários cobrindo cenários de sucesso e falha no pagamento

---

## ⚡ Funcionalidades Principais

### 1️⃣ Assinaturas

* Um usuário pode possuir um ou mais planos
* Cada plano gera faturas periódicas

### 2️⃣ Faturas (Billing)

Cada fatura possui:

* Valor
* Data de vencimento
* Status:

  * `PENDING`
  * `PAID`
  * `FAILED`
  * `OVERDUE`

Pagamento via endpoint:

```
POST /billing/{billingId}/pay
```

---

### 3️⃣ Pagamento Simulado

Ao criar um pagamento:

* É gerado um **paymentUrl fake**
* É criada uma **transactionId única**
* O status inicial da fatura permanece como `PENDING`

Exemplo de resposta:

```json
{
  "billingId": 1,
  "paymentUrl": "https://fake-gateway/pay/tx_123",
  "transactionId": "tx_123",
  "billingStatus": "PENDING"
}
```

---

### 4️⃣ Webhook de Pagamento

Simula a confirmação do gateway de pagamento:

```json
{
  "billingId": 1,
  "event": "payment_approved",
  "transactionId": "tx_123"
}
```

Eventos possíveis:

* `payment_approved`
* `payment_failed`

Ações realizadas:

* Atualiza o status da fatura para `PAID` ou `FAILED`
* Registra a data do pagamento

---

### 5️⃣ Processamento Assíncrono

* Eventos de pagamento são enviados para o **RabbitMQ**
* O consumidor processa os eventos de forma assíncrona
* Simula um fluxo real de integração com gateways externos

---

## 🛠️ Estrutura do Projeto

```
com.payflow.app
├── application
│   ├── controller        # Controllers REST
│   ├── dto               # DTOs de request e response
│   └── mapper            # MapStruct mappers
│
├── domain
│   ├── entities          # Entidades JPA
│   ├── enums             # Enumerações (BillingStatus)
│   └── service           # Regras de negócio
│
├── infrastructure
│   └── repository        # Repositórios JPA
│
└── tests
    └── service           # Testes unitários com Mockito
```

---

## 🔗 Endpoints Principais

| Método | Endpoint                 | Descrição                                    |
| ------ | ------------------------ | -------------------------------------------- |
| GET    | /billing                 | Lista todas as faturas (opcional por userId) |
| GET    | /billing/{billingId}     | Consulta uma fatura pelo ID                  |
| POST   | /billing/{billingId}/pay | Cria pagamento simulado                      |
| POST   | /webhook                 | Recebe evento de pagamento                   |

---

## 🧪 Testes Unitários

Testes escritos com **JUnit** e **Mockito**, simulando dependências como repositórios e mensageria.

### Cenários testados

* Pagamento aprovado → fatura marcada como `PAID`
* Pagamento falho → fatura marcada como `FAILED`

### Exemplo de Teste

```java
@Test
void should_mark_billing_as_failed_when_payment_fails() {
    when(billingRepository.findById(1L)).thenReturn(Optional.of(billing));

    WebhookPaymentRequest request =
        new WebhookPaymentRequest(1L, "payment_failed", "tx_123");

    billingService.processWebhook(request);

    assertEquals(BillingStatus.FAILED, billing.getStatus());
    verify(billingRepository).save(billing);
}
```

---

## 💻 Como Rodar o Projeto (Docker)

Este projeto já está **totalmente configurado para execução via Docker**, não sendo necessário instalar Java, Maven, PostgreSQL ou RabbitMQ localmente.

---

### 1️⃣ Pré-requisitos

* Docker
* Docker Compose

---

### 2️⃣ Clonar o repositório

```bash
git clone https://github.com/seu-usuario/payflow.git
cd payflow
```

---

### 3️⃣ Subir a aplicação com Docker

Na raiz do projeto, execute:

```bash
docker compose up --build
```

Ou em segundo plano:

```bash
docker compose up -d --build
```

Este comando irá subir automaticamente:

* Aplicação Spring Boot
* PostgreSQL
* RabbitMQ

---

### 4️⃣ Acessos

* **API:** [http://localhost:8080](http://localhost:8080)
* **RabbitMQ Management:** [http://localhost:15672](http://localhost:15672)

  * usuário: `guest`
  * senha: `guest`
* **PostgreSQL:** localhost:5432

---

### 5️⃣ Testar os Endpoints

* Postman
* Insomnia
* Thunder Client

---

### ℹ️ Observações

* As **migrations do Flyway** são executadas automaticamente ao subir o container
* Banco de dados e filas são inicializados via Docker Compose
* Ideal para execução local, testes técnicos e demonstração de projeto
