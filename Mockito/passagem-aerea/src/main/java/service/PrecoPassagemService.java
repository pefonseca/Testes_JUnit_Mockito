package service;

import model.Passageiro;
import model.TipoPassageiro;
import model.Voo;

public class PrecoPassagemService {

    public double calcular(Passageiro passageiro, Voo voo) {
        return passageiro.getTipo().getCalculadora().calcular(voo);
    }
}
