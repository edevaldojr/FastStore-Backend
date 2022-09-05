package br.com.faststore.lopestyle.models.enums;

import lombok.Getter;

public enum SexType {
    MALE("Masculino"),
    FEMALE("Feminino"),
    OTHER("Outro");

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