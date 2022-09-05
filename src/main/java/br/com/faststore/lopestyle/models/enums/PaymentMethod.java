package br.com.faststore.lopestyle.models.enums;

import lombok.Getter;

public enum PaymentMethod {
    CREDITO("Crédito"),
    DEBITO("Débito"),
    PIX("Pix");

    @Getter
    private String translateStatus;

    PaymentMethod(String translateStatus) {
        this.translateStatus = translateStatus;
    }

    public static String getTranslateStatus(String name) {
        for (PaymentMethod status : PaymentMethod.values()) {
            if (status.toString().equals(name)) {
                return status.getTranslateStatus();
            }
        }
        return null;
    }
}