package service;

import email.NotificadorEmail;
import model.Pedido;
import model.StatusPedido;
import model.builder.PedidoBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import repository.Pedidos;
import sms.NotificadorSms;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PedidoServiceTest {

    private PedidoService pedidoService;
    private Pedido pedido;

    @Mock
    private Pedidos pedidos;

    @Mock
    private NotificadorEmail notificadorEmail;

    @Mock
    private NotificadorSms notificadorSms;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        List<AcaoLancamentoPedido> acoes = Arrays.asList(pedidos, notificadorEmail, notificadorSms);
        pedidoService = new PedidoService(pedidos, acoes);
        pedido = new PedidoBuilder()
                .comValor(100.0)
                .para("João", "joao@joao.com", "9999-0000")
                .construir();
    }

    @Test
    public void deveCalcularOImposto() throws Exception {
        double imposto = pedidoService.lancar(pedido);
        assertEquals(10.0, imposto, 0.0001);
    }

    @Test
    public void deveSalvarPedidoNoBancoDeDados() throws Exception {
        pedidoService.lancar(pedido);
        verify(pedidos).executar(pedido);
    }

    @Test
    public void deveNotificarPorEmail() throws Exception {
        pedidoService.lancar(pedido);
        verify(notificadorEmail).executar(pedido);
    }

    @Test
    public void deveNotificarPorSms() throws Exception {
        pedidoService.lancar(pedido);
        verify(notificadorSms).executar(pedido);
    }

    @Test
    public void devePagarPedidoPendente() throws Exception {
        Long codigoPedido = 135L;

        Pedido pedidoPendente = new Pedido();
        pedidoPendente.setStatus(StatusPedido.PENDENTE);
        when(pedidos.buscarPeloCodigo(codigoPedido)).thenReturn(pedidoPendente);

        Pedido pedidoPago = pedidoService.pagar(codigoPedido);

        assertEquals(StatusPedido.PAGO, pedidoPago.getStatus());
    }

    @Test(expected = StatusPedidoInvalidoException.class)
    public void deveNegarPagamento() throws Exception {
        Long codigoPedido = 135L;

        Pedido pedidoPendente = new Pedido();
        pedidoPendente.setStatus(StatusPedido.PAGO);
        when(pedidos.buscarPeloCodigo(codigoPedido)).thenReturn(pedidoPendente);

        pedidoService.pagar(codigoPedido);
    }
}
