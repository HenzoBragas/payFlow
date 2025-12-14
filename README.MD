PayFlow

Sistema de gerenciamento de assinaturas, faturamento e pagamentos simulados, construído em Java Spring Boot com integração de filas assíncronas via RabbitMQ.

🏗 Tecnologias utilizadas

Backend: Java 23, Spring Boot 4

Banco de dados: PostgreSQL

ORM: Spring Data JPA / Hibernate

Mensageria: RabbitMQ

Testes unitários: JUnit 6, Mockito

Mapeamento DTO: MapStruct

Controle de versão: Git + GitHub

📌 Descrição do projeto

O PayFlow é um sistema de gestão de assinaturas e faturamento que permite:

Cadastro de usuários e planos de assinatura.

Criação de assinaturas vinculadas a usuários e planos.

Geração automática de faturas (Billing) associadas às assinaturas.

Simulação de pagamentos via endpoint /billing/{id}/pay.

Validação de pagamentos através de webhook.

Processamento assíncrono de eventos via RabbitMQ.

Mudança automática do status da fatura (PENDING, PAID, FAILED) de acordo com o evento de pagamento.

Testes unitários simulando cenários de pagamento aprovado e falho.

⚡ Funcionalidades principais
1. Assinaturas

Um usuário pode ter um ou mais planos.

Cada plano gera faturas periódicas (Billing).

2. Faturas (Billing)

Status: PENDING, PAID, FAILED, OVERDUE.

Possui data de vencimento e valor.

Pode ser paga via endpoint POST /billing/{billingId}/pay.

3. Pagamento simulado

Ao criar pagamento, é gerado um link fake e uma transactionId única.

Status inicial da fatura: PENDING.

Webhook simula confirmação ou falha do pagamento:

{
"billingId": 1,
"event": "payment_approved",
"transactionId": "tx_123"
}

4. Integração assíncrona

RabbitMQ é usado para simular eventos de pagamento.

Permite testar processamento assíncrono e webhooks de forma realista.

🛠 Estrutura do projeto
com.payflow.app
├─ application
│  ├─ controller           # Controllers REST
│  ├─ dto                  # DTOs de request e response
│  └─ mapper               # MapStruct mappers
├─ domain
│  ├─ entities             # Entidades JPA
│  ├─ enums                # Enumerações (BillingStatus)
│  └─ service              # Lógica de negócio
├─ infrastructure
│  └─ repository           # Repositórios Spring Data JPA
└─ tests
└─ service              # Testes unitários com Mockito

🔗 Endpoints principais
Método	Endpoint	Descrição
GET	/billing	Lista todas as faturas (opcional por userId)
GET	/billing/{billingId}	Consulta uma fatura pelo ID
POST	/billing/{billingId}/pay	Cria pagamento simulado (gera paymentUrl e transactionId)
POST	/webhook	Recebe evento de pagamento (aprovado ou falho)
Exemplo de POST /billing/{billingId}/pay

Request:

{}


Response:

{
"billingId": 1,
"paymentUrl": "https://fake-gateway/pay/tx_123",
"transactionId": "tx_123",
"billingStatus": "PENDING"
}

Exemplo de Webhook

Request:

{
"billingId": 1,
"event": "payment_approved",
"transactionId": "tx_123"
}


Ação:

Altera status da fatura para PAID ou FAILED de acordo com o evento.

Registra data do pagamento.

🧪 Testes

Testes unitários com JUnit 6 e Mockito.

Cenários testados:

Pagamento aprovado → fatura marcada como PAID.

Pagamento falho → fatura marcada como FAILED.

Exemplo de teste com Mockito:

@Test
void should_mark_billing_as_failed_when_payment_fails() {
when(billingRepository.findById(1L)).thenReturn(Optional.of(billing));
WebhookPaymentRequest request = new WebhookPaymentRequest(1L, "payment_failed", "tx_123");
billingService.processWebhook(request);
assertEquals(BillingStatus.FAILED, billing.getStatus());
verify(billingRepository).save(billing);
}

💻 Como rodar o projeto

Clonar repositório:

git clone https://github.com/seu-usuario/payflow.git


Configurar PostgreSQL e criar banco payflow.

Configurar RabbitMQ local ou docker.

Ajustar application.properties com credenciais do banco e RabbitMQ.

Rodar projeto:

mvn spring-boot:run


Testar endpoints com Postman ou Insomnia.