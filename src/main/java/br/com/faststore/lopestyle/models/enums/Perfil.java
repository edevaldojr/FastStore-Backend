package br.com.faststore.lopestyle.models.enums;

public enum Perfil {
    
    ADMIN(1, "ROLE_ADMIN"),
    EMPLOYEE(2, "ROLE_EMPLOYEE"),
    CONSUMER(3, "ROLE_CONSUMER");

    private int code;
    private String description;

    private Perfil(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Perfil toEnum(Integer code) {

        if(code==null) {
            return null;
        }

        for(Perfil x : Perfil.values()) {
            if(code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + code);
    }
    
}
