package br.com.faststore.lopestyle.models.enums;

import lombok.Getter;

public enum OrderStatus {

    PENDING("Pedido Pendente"),
    PREPARING("Preparando Pedido"),
    POSTED("Pedido Enviado"),
    FINISHED("Pedido Finalizado");
            
    @Getter
    private String translateStatus;

    OrderStatus(String translateStatus) {
        this.translateStatus = translateStatus;
    }

    public static String getTranslateStatus(String name) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.toString().equals(name)) {
                return status.getTranslateStatus();
            }
        }
        return "Erro ao traduzir OrderStatus";
    }
}