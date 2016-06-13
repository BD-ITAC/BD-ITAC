package br.ita.bditac.ws.client.alerta;

import junit.framework.TestCase;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import br.ita.bditac.ws.client.CategoriasClient;
import br.ita.bditac.ws.model.Categoria;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoriasTest extends TestCase {

    private static final String HOST_URL = "http://200.144.14.28/rest/categories";

    @Test
    public void test01GetCategorias() {
        CategoriasClient categoriasClient = new CategoriasClient(HOST_URL);
        List<Categoria> categorias = categoriasClient.getCategorias();
        assertNotNull(categorias);
    }

}
