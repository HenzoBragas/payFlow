package com.payflow.app.domain.entities.enums;

public enum BillingStatus {
    PENDING , //Criada, aguardando pagamento
    PAID, //Paga com sucesso
    OVERDUE, //Vencida
    CANCELED, //Cancelda automaticamente
    FAILED, //Tentativa de pagamento falhow
}
