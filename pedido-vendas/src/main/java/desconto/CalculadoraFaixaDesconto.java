package desconto;

public abstract class CalculadoraFaixaDesconto {

    private CalculadoraFaixaDesconto proximo;

    public CalculadoraFaixaDesconto(CalculadoraFaixaDesconto proximo) {
        this.proximo = proximo;
    }

    protected abstract double calcular(double valorTotal);

    public double desconto(double valorTotal) {
        double desconto = calcular(valorTotal);

        if (desconto == -1) return proximo.desconto(valorTotal);

        return desconto;
    }
}
