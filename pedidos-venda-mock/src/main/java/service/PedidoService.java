package service;

import email.NotificadorEmail;
import model.Pedido;
import model.StatusPedido;
import repository.Pedidos;
import sms.NotificadorSms;

import java.util.List;

public class PedidoService {

    private List<AcaoLancamentoPedido> acoes;
    private Pedidos pedidos;

    public PedidoService(Pedidos pedidos, List<AcaoLancamentoPedido> acoes) {
        this.acoes = acoes;
        this.pedidos = pedidos;
    }

    public double lancar(Pedido pedido) {
        double imposto = pedido.getValor() * 0.1;

        acoes.forEach(a -> a.executar(pedido));

        return imposto;
    }

    public Pedido pagar(Long codigo) {
        Pedido pedido = pedidos.buscarPeloCodigo(codigo);

        if (!pedido.getStatus().equals(StatusPedido.PENDENTE))
            throw new StatusPedidoInvalidoException();

        pedido.setStatus(StatusPedido.PAGO);
        return pedido;
    }
}
