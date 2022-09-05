package br.com.faststore.lopestyle.models.enums;

import lombok.Getter;

public enum PaymentStatus {
    PAID("Pago"),
    REFUSED("Recusado");

    @Getter
    private String translateStatus;

    PaymentStatus(String translateStatus) {
        this.translateStatus = translateStatus;
    }

    public static String getTranslateStatus(String name) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.toString().equals(name)) {
                return status.getTranslateStatus();
            }
        }
        return null;
    }
}
