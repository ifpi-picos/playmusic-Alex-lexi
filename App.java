import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.util.List;

import dominios.Album;
import dominios.Artista;
import dominios.Musica;

public class App {
    public static void main(String[] args) throws Exception {
        // Configuração inicial
        Musica musica1 = new Musica();
        musica1.setNome("Magica");
        musica1.setDuracao(120);
        musica1.setArquivoAudio("./music/calcinhaPreta.wav");
        musica1.setGenero("forró");

        Musica musica2 = new Musica();
        musica2.setNome("E o pente");
        musica2.setDuracao(120);
        musica2.setArquivoAudio("./music/funkk.wav");
        musica2.setGenero("funk");

        Musica musica3 = new Musica();
        musica3.setNome("Lembranças");
        musica3.setDuracao(120);
        musica3.setArquivoAudio("./music/hungria.wav");
        musica3.setGenero("hip hop");

        Album album1 = new Album();
        album1.setNome("Primeiro album");
        album1.setAno(2000);
        album1.addMusica(musica1);
        album1.addMusica(musica2);
        album1.addMusica(musica3);

        Artista bandaCalcinhaPreta = new Artista();
        bandaCalcinhaPreta.setNome("Mágica");
        bandaCalcinhaPreta.addAlbum(album1);

        System.out.println("Abrindo o PlayMusic");

        List<Musica> musicas = bandaCalcinhaPreta.getAlbuns().get(0).getMusicas();
        int[] currentIndex = {0}; // Índice da música atualmente tocando

        // Player de áudio
        AudioPlayer player = new AudioPlayer();
        player.loadAudio(musicas.get(currentIndex[0]).getArquivoAudio());

        // Botão Play/Stop
        JButton playStopButton = new JButton("Play");
        playStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!player.isPlaying) {
                    player.playAudio();
                    playStopButton.setText("Stop");
                } else {
                    player.stopAudio();
                    playStopButton.setText("Play");
                }
            }
        });

        // Botão Próxima Música
        JButton nextButton = new JButton("Próxima");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex[0] < musicas.size() - 1) {
                    currentIndex[0]++;
                } else {
                    currentIndex[0] = 0; // Volta para o início se estiver no final
                }
                player.stopAudio();
                player.loadAudio(musicas.get(currentIndex[0]).getArquivoAudio());
                playStopButton.setText("Play");
                System.out.println("Tocando: " + musicas.get(currentIndex[0]).getNome());
            }
        });

        // Botão Música Anterior
        JButton previousButton = new JButton("Anterior");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex[0] > 0) {
                    currentIndex[0]--;
                } else {
                    currentIndex[0] = musicas.size() - 1; // Vai para o final se estiver no início
                }
                player.stopAudio();
                player.loadAudio(musicas.get(currentIndex[0]).getArquivoAudio());
                playStopButton.setText("Play");
                System.out.println("Tocando: " + musicas.get(currentIndex[0]).getNome());
            }
        });

        // Exibe um JOptionPane com os botões
        JOptionPane.showOptionDialog(
                null,
                "Tocando: " + musicas.get(currentIndex[0]).getNome(),
                "PlayMusic",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[] {  previousButton, playStopButton, nextButton },
                playStopButton // Botão padrão
        );

        // Fecha o clip de áudio ao encerrar o programa
        if (player.audioClip != null) {
            player.audioClip.close();
        }
    }
}
