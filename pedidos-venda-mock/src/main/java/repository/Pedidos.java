package repository;

import model.Pedido;
import service.AcaoLancamentoPedido;

public class Pedidos implements AcaoLancamentoPedido {

    @Override
    public void executar(Pedido pedido) {
        System.out.println("Salvando no banco de dados...");
    }

    public Pedido buscarPeloCodigo(Long codigo) {
        return new Pedido();
    }
}
