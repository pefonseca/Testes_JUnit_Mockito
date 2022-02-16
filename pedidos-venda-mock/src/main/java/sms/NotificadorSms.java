package sms;

import model.Pedido;
import service.AcaoLancamentoPedido;

public class NotificadorSms implements AcaoLancamentoPedido {

    @Override
    public void executar(Pedido pedido) {
        System.out.println("Enviando o sms...");
    }
}
