package com.payflow.app.domain.entities.enums;

public enum StatusSubscription {

    /**
     * Assinatura ativa e com pagamento em dia.
     * Usuário tem acesso total ao plano.
     */
    ACTIVE,

    /**
     * Assinatura criada, mas pagamento ainda não confirmado.
     * Usuário pode estar com acesso limitado.
     */
    PENDING_PAYMENT,

    /**
     * Pagamento atrasado ou problema no pagamento.
     * Acesso temporariamente suspenso.
     */
    SUSPENDED,

    /**
     * Assinatura finalizada por término do período.
     */
    EXPIRED,

    /**
     * Assinatura cancelada pelo usuário ou pelo sistema.
     */
    CANCELED
}
