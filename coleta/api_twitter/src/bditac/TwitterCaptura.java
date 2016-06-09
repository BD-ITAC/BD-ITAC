/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bditac;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Henrique
 */
public class TwitterCaptura {

    /**
     * @param args the command line arguments
     * @throws twitter4j.TwitterException
     */
    static List<Ocorrencia> ocors = new ArrayList();
    static EntityManagerFactory factory = Persistence.createEntityManagerFactory("BDITACPU");

    public static void main(String[] args) throws TwitterException {

        int criid, apiid, capid;

        if (args.length == 3) {
            criid = Integer.parseInt(args[0]);
            apiid = Integer.parseInt(args[1]);
            capid = Integer.parseInt(args[2]);
        } else {
            criid = 1;
            apiid = 1;
            capid = 1;
        }

        CriseJpaController crijpa = new CriseJpaController(factory);

        Crise crise = crijpa.findCrise(criid);

        CriseApiJpaController criapijpa = new CriseApiJpaController(factory);

        CriseApi criapi = criapijpa.findCriseApi(capid);

        ApiJpaController apijpa = new ApiJpaController(factory);

        Api api = apijpa.findApi(apiid);

        CidadeJpaController cidjpa = new CidadeJpaController(factory);

        Cidade cidade = cidjpa.findCidade(crise.getCidId());

        String coords[] = crise.getCriRegiao().split(",");

        TwitterGeo geo = new TwitterGeo(coords, cidade.getCidNome(), crise.getCriGeotipo());

        Thread threadGeo = new Thread(geo);

        TwitterMens mens = new TwitterMens(crise.getCrtId());

        Thread threadMens = new Thread(mens);

        TwitterGravar gravar = new TwitterGravar();

        Thread threadGravar = new Thread(gravar);

        threadGeo.setName("ThreadGeo");
        threadGeo.start();

        threadMens.setName("ThreadMens");
        threadMens.start();

        threadGravar.setName("ThreadGravar");
        threadGravar.start();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(criapi.getCapKey())
                .setOAuthConsumerSecret(criapi.getCapSecret())
                .setOAuthAccessToken(criapi.getCapToken())
                .setOAuthAccessTokenSecret(criapi.getCapTokenSecret());

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        String texto = "[\\xF0\\x9F]";

        StatusListener listener;
        listener = new StatusListener() {
            int cont = 0;

            @Override
            public void onStatus(Status status) {
                if (!(status.getText().contains(texto) || status.getText().isEmpty())) {
                    Ocorrencia ocor = new Ocorrencia();
                    ocor.setApiId(apiid);
                    ocor.setCriId(criid);
                    ocor.setCapId(capid);

                    ocor.setOcrIdApi(status.getId());
                    ocor.setOcrCriacao(status.getCreatedAt());
                    ocor.setOcrTexto(status.getText());
//                    System.out.println(ocor.getOcrTexto());
                    ocor.setOcrUsuId(status.getUser().getId());
                    ocor.setOcrUsuNome(status.getUser().getName());
                    ocor.setOcrUsuScreenNome(status.getUser().getScreenName());
                    ocor.setOcrFonte(status.getSource());
                    ocor.setOcrLingua(status.getLang());
                    ocor.setOcrFavorite(status.getFavoriteCount());
                    ocor.setOcrRetweet(status.getRetweetCount());
                    String coords = "";
                    if (status.getPlace() == null) {
                        ocor.setOcrPaisCodigo("");
                        ocor.setOcrPais("");
                        ocor.setOcrLocal("");
                    } else {
                        ocor.setOcrPaisCodigo(status.getPlace().getCountryCode());
                        ocor.setOcrPais(status.getPlace().getCountry());
                        ocor.setOcrLocal(status.getPlace().getFullName());
                        GeoLocation locs[][] = status.getPlace().getBoundingBoxCoordinates();
                        for (int x = 0; x < locs.length; x++) {
                            for (int y = 0; y < locs[x].length; y++) {
                                coords += "[" + locs[x][y].getLongitude() + "," + locs[x][y].getLatitude() + "]";
                                if (!(x == locs.length - 1 && y == locs[x].length - 1)) {
                                    coords += ",";
                                }
                            }
                        }
                    }

                    ocor.setOcrCoordenadas(coords);
                    ocor.setOcrGeo('0');
                    ocor.setOcrIdentificacao('0');
                    ocor.setOcrIdenper(0.0f);
                    ocor.setOcrGravado('0');
                    ocor.setOcrSentimento('0');
                    ocor.setOcrTempo('0');

                    boolean add = ocors.add(ocor);

                    cont++;

                    if (ocors.size() > 1000) {
                        Limpar();
                    }

//                    System.out.println(cont+" - "+status.getId() + " - " + status.getCreatedAt() + status.getPlace().getFullName());
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }

            private void Limpar() {
                while (!threadGeo.isInterrupted()) {
                    threadGeo.interrupt();
                }
                while(!threadMens.isInterrupted()){
                    threadMens.interrupt();
                }
                while(!threadGravar.isInterrupted()){
                    threadGravar.interrupt();
                }
                boolean achou = true;
                int x = 0;
                System.out.println("Removendo: " + ocors.size());
                while (x < ocors.size()) {
                    if (ocors.get(x).getOcrGravado() != '0') {
                        ocors.remove(x);
                    } else {
                        x++;
                    }
                }
                System.out.println("Final: " + ocors.size());
                if(!threadGeo.isAlive()){
                    threadGeo.start();
                }
                if(!threadMens.isAlive()){
                    threadMens.start();
                }
                if(!threadGravar.isAlive()){
                    threadGravar.start();
                }
            }
        };

        FilterQuery filter = new FilterQuery();

        double[][] location = new double[2][2];

        location[0][0] = Double.parseDouble(coords[0]);
        location[0][1] = Double.parseDouble(coords[1]);
        location[1][0] = Double.parseDouble(coords[4]);
        location[1][1] = Double.parseDouble(coords[5]);

        filter.locations(location);

        twitterStream.addListener(listener);

        twitterStream.filter(filter);
    }
}
