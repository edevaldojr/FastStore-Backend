package br.com.faststore.lopestyle.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;

@Data
public class Order {

    public enum OrderStatus {

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
    
    private int id;
    private OrderStatus status;
    private Consumer consumer;
    private List<OrderProduct> orderProduct;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
