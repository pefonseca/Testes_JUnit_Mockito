package email;

import model.Pedido;
import service.AcaoLancamentoPedido;

public class NotificadorEmail implements AcaoLancamentoPedido {

    @Override
    public void executar(Pedido pedido) {
        System.out.println("Enviando o e-mail...");
    }
}
