/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditacsimul;

import static java.lang.Thread.sleep;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Henrique
 */
public class BDITACSIMUL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<CriseEstacao> ces = new ArrayList();
        List<CriseSensor> css = new ArrayList();
        List<Estacao> ests = new ArrayList();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("BDITACSIMULPU");

        CriseEstacaoJpaController cesjpa = new CriseEstacaoJpaController(factory);

        ces = cesjpa.findCriseEstacaoCriseId(1);

        CriseSensorJpaController cssjpa = new CriseSensorJpaController(factory);

        css = cssjpa.findCriseSensorEntities();

        EstacaoJpaController estjpa = new EstacaoJpaController(factory);

        Float cssp[][] = new Float[css.size()][5];

// 1- Temperatura 2- Pressão 4-Nível 10- Outros/Umidade 11- Pluviômetro
        String data = "";

        int horasort = 0, crisecount = 0, tempos = 30, ocorhora = 4, c=0;

        Random gerador = new Random();
        while (true) {
            try {
                if (data.isEmpty() || !data.equals(Util.DatetoStr(new Date()))) {
                    data = Util.DatetoStr(new Date());
                    c=0;
                    while (horasort <= Integer.parseInt(Util.lerHora().substring(0, 2)) && c < 10) {
                        for(int x=0;x<9;x++){
                            horasort = gerador.nextInt(23);
                        }
                        c++;
                    }
                    if(horasort<=Integer.parseInt(Util.lerHora().substring(0, 2))){
                        horasort = Integer.parseInt(Util.lerHora().substring(0, 2))+1;
                    }
                    System.out.println("Início: " + horasort + " h");
                    crisecount = 0;
                    for (int x = 0; x < cssp.length; x++) {
                        for (int y = 0; y < cssp[x].length; y++) {
                            cssp[x][y] = 0f;
                        }
                    }
                }
            } catch (ParseException ex) {
                System.out.println("Geração Errada de Data...");
            }
            try {
                sleep(tempos * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BDITACSIMUL.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (horasort == Integer.parseInt(Util.lerHora().substring(0, 2)) && crisecount < tempos * ocorhora) {
                System.out.println("Dados Sequenciais: " + Util.lerHora());
                crisecount++;
                for (CriseEstacao ce : ces) {
                    Estacao est = new Estacao();
                    est.setCreId(ce.getCreId());
                    int cont = 0;
                    for (CriseSensor cs : css) {
                        if (cs.getCreId() == ce.getCreId()) {
                            switch (cs.getCstId()) {
                                case 1: // temperatura
                                    if (cssp[cont][0] == 0) {
                                        cssp[cont][0] = 1f;
                                        cssp[cont][1] = cs.getCseAlerta() - gerador.nextInt(2);
                                        cssp[cont][2] = cs.getCseCritico() + gerador.nextInt(2);
                                        cssp[cont][3] = (cssp[cont][2] - cssp[cont][1]) / (tempos * ocorhora);
                                        cssp[cont][4] = cssp[cont][1];
                                    } else {
                                        cssp[cont][4] = cssp[cont][4] + cssp[cont][3];
                                    }
                                    if (cssp[cont][4] > cs.getCseFaixafim()) {
                                        cssp[cont][4] = cs.getCseFaixafim();
                                    }
                                    est.setEstTemperatura(cssp[cont][4]);
                                    break;
                                case 2: // pressão
                                    if (cssp[cont][0] == 0) {
                                        cssp[cont][0] = 1f;
                                        cssp[cont][1] = cs.getCseAlerta() - gerador.nextInt(2);
                                        cssp[cont][2] = cs.getCseCritico() + gerador.nextInt(2);
                                        cssp[cont][3] = (cssp[cont][2] - cssp[cont][1]) / (tempos * ocorhora);
                                        cssp[cont][4] = cssp[cont][1];
                                    } else {
                                        cssp[cont][4] = cssp[cont][4] + cssp[cont][3];
                                    }
                                    if (cssp[cont][4] > cs.getCseFaixafim()) {
                                        cssp[cont][4] = cs.getCseFaixafim();
                                    }
                                    est.setEstBarometrica(cssp[cont][4]);
                                    break;
                                case 4: // nível
                                    if (cssp[cont][0] == 0) {
                                        cssp[cont][0] = 1f;
                                        cssp[cont][1] = cs.getCseAlerta() - gerador.nextInt(2);
                                        cssp[cont][2] = cs.getCseCritico() + gerador.nextInt(2);
                                        cssp[cont][3] = (cssp[cont][2] - cssp[cont][1]) / (tempos * ocorhora);
                                        cssp[cont][4] = cssp[cont][1];
                                    } else {
                                        cssp[cont][4] = cssp[cont][4] + cssp[cont][3];
                                    }
                                    if (cssp[cont][4] > cs.getCseFaixafim()) {
                                        cssp[cont][4] = cs.getCseFaixafim();
                                    }
                                    est.setEstNivelagua(cssp[cont][4]);
                                    break;
                                case 10: // umidade
                                    if (cssp[cont][0] == 0) {
                                        cssp[cont][0] = 1f;
                                        cssp[cont][1] = cs.getCseAlerta() - gerador.nextInt(10);
                                        cssp[cont][2] = cs.getCseCritico() + gerador.nextInt(10);
                                        cssp[cont][3] = (cssp[cont][2] - cssp[cont][1]) / (tempos * ocorhora);
                                        cssp[cont][4] = cssp[cont][1];
                                    } else {
                                        cssp[cont][4] = cssp[cont][4] + cssp[cont][3];
                                    }
                                    if (cssp[cont][4] > cs.getCseFaixafim()) {
                                        cssp[cont][4] = cs.getCseFaixafim();
                                    }
                                    est.setEstUmidade(cssp[cont][4]);
                                    break;
                                case 11: // pluviômetro
                                    if (cssp[cont][0] == 0) {
                                        cssp[cont][0] = 1f;
                                        cssp[cont][1] = cs.getCseAlerta() - gerador.nextInt(5);
                                        cssp[cont][2] = cs.getCseCritico() + gerador.nextInt(5);
                                        cssp[cont][3] = (cssp[cont][2] - cssp[cont][1]) / (tempos * ocorhora);
                                        cssp[cont][4] = cssp[cont][1];
                                    } else {
                                        cssp[cont][4] = cssp[cont][4] + cssp[cont][3];
                                    }
                                    if (cssp[cont][4] > cs.getCseFaixafim()) {
                                        cssp[cont][4] = cs.getCseFaixafim();
                                    }
                                    est.setEstPluviometro(cssp[cont][4]);
                                    break;
                            }
                        }
                        cont++;
                    }
                    est.setEstDatahora(new Date());
                    est.setEstLido('N');
                    ests.add(est);
                    for (Estacao e : ests) {
                        try {
                            estjpa.create(e);
                        } catch (Exception ex) {
                            System.out.println("Erro ao gravar no BD: " + ests.size());
                            break;
                        }
                        e.setEstLido('S');
                    }
                    int x = 0;
                    while (x < ests.size()) {
                        if (ests.get(x).getEstLido() == 'S') {
                            ests.remove(x);
                        } else {
                            x++;
                        }
                    }
                }
            } else {
                System.out.println("Dados Aleatórios: " + Util.lerHora());
                // dados aleatorios
                for (CriseEstacao ce : ces) {
                    Estacao est = new Estacao();
                    est.setCreId(ce.getCreId());
                    est.setEstNota("");
                    for (CriseSensor cs : css) {
                        if (cs.getCreId() == ce.getCreId()) {
                            switch (cs.getCstId()) {
                                case 1:
                                    est.setEstTemperatura(GerarRand(-1, cs.getCseFaixaini(), cs.getCseAlerta(), cs.getCseFaixafim(), 5));
                                    if (est.getEstTemperatura() < cs.getCseFaixaini() || est.getEstTemperatura() > cs.getCseFaixafim()) {
                                        est.setEstNota(est.getEstNota() + "falha temp.;");
                                    }
                                    break;
                                case 2:
                                    est.setEstBarometrica(GerarRand(0, cs.getCseFaixaini(), cs.getCseAlerta(), cs.getCseFaixafim(), 10));
                                    if (est.getEstBarometrica() < cs.getCseFaixaini() || est.getEstBarometrica() > cs.getCseFaixafim()) {
                                        est.setEstNota(est.getEstNota() + "falha pres;");
                                    }
                                    break;
                                case 4:
                                    est.setEstNivelagua(GerarRand(-1, cs.getCseFaixaini(), cs.getCseAlerta(), cs.getCseFaixafim(), 5));
                                    if (est.getEstNivelagua() < cs.getCseFaixaini() || est.getEstNivelagua() > cs.getCseFaixafim()) {
                                        est.setEstNota(est.getEstNota() + "falha nível;");
                                    }
                                    break;
                                case 10:
                                    est.setEstUmidade(GerarRand(0, cs.getCseFaixaini(), cs.getCseAlerta(), cs.getCseFaixafim(), 30));
                                    if (est.getEstUmidade() < cs.getCseFaixaini() || est.getEstUmidade() > cs.getCseFaixafim()) {
                                        est.setEstNota(est.getEstNota() + "falha umid.;");
                                    }
                                    break;
                                case 11:
                                    est.setEstPluviometro(GerarRand(-1, cs.getCseFaixaini(), cs.getCseAlerta(), cs.getCseFaixafim(), 10));
                                    if (est.getEstPluviometro() < cs.getCseFaixaini() || est.getEstPluviometro() > cs.getCseFaixafim()) {
                                        est.setEstNota(est.getEstNota() + "falha pluv.;");
                                    }
                                    break;
                            }
                        }
                    }
                    est.setEstDatahora(new Date());
                    est.setEstLido('N');
                    ests.add(est);
                    for (Estacao e : ests) {
                        try {
                            estjpa.create(e);
                        } catch (Exception ex) {
                            ests.add(est);
                            System.out.println("Erro ao gravar no BD: " + ests.size());
                            break;
                        }
                        e.setEstLido('S');
                    }
                    int x = 0;
                    while (x < ests.size()) {
                        if (ests.get(x).getEstLido() == 'S') {
                            ests.remove(x);
                        } else {
                            x++;
                        }
                    }
                }
            }
        }
    }

    public static float GerarRand(float inicio, float faxini, float alerta, float faxfim, int inc) {
        float ini, fim;
        Random gerador = new Random();
        int sort = gerador.nextInt(50);
        float num;
        int x = 1;
        if (sort <= 5) {
            ini = inicio;
            fim = faxini;
            num = fim + 1;
            while (num > fim) {
                x++;
                num = (float) (Math.random() * fim);
            }
        } else if (sort <= 45) {
            ini = faxini;
            fim = alerta;
            num = fim + 1;
            while (num > fim) {
                x++;
                num = (float) (Math.random() * (fim - ini) + ini);
            }
        } else {
            ini = faxfim;
            fim = faxfim + gerador.nextInt(inc);
            num = fim + 1;
            while (num > fim) {
                x++;
                num = (float) (Math.random() * fim);
            }
        }
        return num;
    }
}
