package net.my4x.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.junit.Test;


public class GenerateTextTest {


    private static final String TEMPLATE = "INSERT INTO PIECE_A_FOURNIR_PAR_TYPE_DOSSIER_ET_AFFAIRE_HUB (typeDossier, typeAffaire, pieceAFournir_code) VALUES ('{{type}}', 'SOUSCRIPTION_SCPI', '{{piece}}');\n";

    private static String[] typesDossier = {"PATRIMMOCOMMERCE","PRIMOFAMILY","PRIMOPIERRE","PRIMOVIE"};
    @Test
    public void shouldGenerateText() throws IOException {
        StringBuilder output = new StringBuilder();
        InputStream inputStream = this.getClass().getResourceAsStream("/input.txt");

        List<String> strings = IOUtils.readLines(inputStream);
        SortedSet<String> sortedSet = new TreeSet();
        sortedSet.addAll(strings);
        Sets.newHashSet(typesDossier).stream().forEach(type ->
            sortedSet.stream()
                    .forEach(word -> output.append(TEMPLATE.replace("{{piece}}",word).replace("{{type}}",type)))
        );
        String text = output.toString();
        System.out.println(text);
    }
}
