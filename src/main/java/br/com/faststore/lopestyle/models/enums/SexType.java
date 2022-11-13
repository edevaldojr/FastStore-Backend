package br.com.faststore.lopestyle.models.enums;

public enum SexType {
    MALE(0,"Masculino"),
    FEMALE(1,"Feminino"),
    OTHER(2, "Outro");

    private int code;
    private String description;

    private SexType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static SexType toEnum(Integer code) {

        if(code==null) {
            return null;
        }

        for(SexType x : SexType.values()) {
            if(code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + code);
    }

    public static int toInt(SexType sexType) {

        if(sexType==null) {
            return -1;
        }

        for(SexType x : SexType.values()) {
            if(sexType.description.equals(x.getDescription())) {
                return x.code;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + sexType);
    }
    
}