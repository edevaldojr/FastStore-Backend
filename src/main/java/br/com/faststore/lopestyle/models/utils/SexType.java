package br.com.faststore.lopestyle.models.utils;

import lombok.Getter;

public enum SexType {
    MALE("Masculino"),
    FAMELA("Feminino"),
    OUTRO("Outro");

    @Getter
    private String translateStatus;

    SexType(String translateStatus) {
        this.translateStatus = translateStatus;
    }

    public static String getTranslateStatus(String name) {
        for (SexType status : SexType.values()) {
            if (status.toString().equals(name)) {
                return status.getTranslateStatus();
            }
        }
        return null;
    }
}