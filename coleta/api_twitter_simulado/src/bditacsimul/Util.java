/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bditacsimul;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Henrique Louro
 */
public class Util {

    static String data;
    static String hora;
    static SimpleDateFormat formata_data = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat formata_hora = new SimpleDateFormat("HH:mm:ss");
    public static boolean primdig = false;

    public static String lerHora() {
        Date horaAtual = new Date();
        hora = formata_hora.format(horaAtual);
        return hora;
    }

    public static String lerData() {
        Date dataAtual = new Date();
        data = formata_data.format(dataAtual);
        return data;
    }

    public static String lerDataMySQL() {
        Date dataAtual = new Date();
        data = formata_data.format(dataAtual);
        data = data.substring(6) + "-" + data.substring(3, 5) + "-" + data.substring(0, 2);
        return data;
    }

    public static String convDatatoMySQL(String dat) throws ParseException {

        DateFormat dfInput = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dfMySQL = new SimpleDateFormat("yyyy-MM-dd");

        Date datac = dfInput.parse(dat); //<< == isso pode lançar exceções é preciso try{}catch{}
        String formatoMySql = dfMySQL.format(datac);

        return formatoMySql;
    }

    public static String convHoratoMySQL(String hora) throws ParseException {
        DateFormat hfInput = new SimpleDateFormat("HH:mm:ss");
        DateFormat hfMySQL = new SimpleDateFormat("HH:mm:ss");

        Date horac = hfInput.parse(hora); //<< == isso pode lançar exceções é preciso try{}catch{}
        String formatoMySql = hfMySQL.format(horac);

        return formatoMySql;

    }

    public static String convDateTimetoStr(String dat) throws ParseException {

        DateFormat dfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dfMySQL = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Date datac = dfInput.parse(dat); //<< == isso pode lançar exceções é preciso try{}catch{}
        String formatoMySql = dfMySQL.format(datac);

        return formatoMySql;
    }

    public static String convStrtoDateTimeto(String dat) throws ParseException {

        DateFormat dfInput = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DateFormat dfMySQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date datac = dfInput.parse(dat); //<< == isso pode lançar exceções é preciso try{}catch{}
        String formatoMySql = dfMySQL.format(datac);

        return formatoMySql;
    }

    public static String convDatetoStr(String dat) throws ParseException {

        if (!dat.equals("0000-00-00 00:00:00")) {
            DateFormat dfInput = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dfMySQL = new SimpleDateFormat("dd/MM/yyyy");

            Date datac = dfInput.parse(dat); //<< == isso pode lançar exceções é preciso try{}catch{}
            String formatoMySql = dfMySQL.format(datac);

            return formatoMySql;
        } else {
            return "";
        }
    }

    public static String DatetoStr(Date dat) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        if (dat == null) {
            return "00/00/0000";
        } else {
            String formdat = df.format(dat);

            return formdat;
        }
    }

    public static String DateTimetoStr(Date dat) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");

        if (dat == null) {
            return "00/00/0000 00:00:00";
        } else {
            String formdat = df.format(dat);
            return formdat;
        }
    }

    public static Date StrtoDate(String dat) throws ParseException {

//        DateFormat dfi = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DateFormat dfo = new SimpleDateFormat("dd/MM/yyyy");

        Date datac = dfo.parse(dat); //<< == isso pode lançar exceções é preciso try{}catch{}
//        String formatoMySql = dfMySQL.format(datac);

        return datac;
    }

    public static String convTimetoStr(String dat) throws ParseException {

        DateFormat dfInput = new SimpleDateFormat("HH:mm:ss");
        DateFormat dfMySQL = new SimpleDateFormat("HH:mm:ss");

        Date datac = dfInput.parse(dat); //<< == isso pode lançar exceções é preciso try{}catch{}
        String formatoMySql = dfMySQL.format(datac);

        return formatoMySql;
    }

    public static String encode(String str, String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        key = sha1(key);

        int strLen = str.length();
        int keyLen = key.length();

        int j = 0;
        String hash = "";
        int ordStr = 0;
        int ordKey = 0;

        for (int i = 0; i < strLen; i++) {

            ordStr = (int) str.charAt(i);//ord(str.substring(i, 1).);

            if (j == keyLen) {
                j = 0;
            }

            ordKey = (int) key.charAt(j);//ord(key.substring(j, 1));

            j++;

//            hash .= strrev(base_convert(dechex($ordStr + $ordKey), 16, 36));
            hash += StrRev(BaseConverterUtil.toBase36(BaseConverterUtil.fromBase16(Integer.toHexString(ordStr + ordKey))));

        }

        return hash;
    }

    public static String decode(String str, String key) {
//	$key	= sha1( $key );
//	$strLen	= strlen( $string );
//	$keyLen	= strlen( $key );

//	$j		= 0;
        String hash = "";

//	for( $i = 0 ; $i < $strLen ; $i += 2)
//	{
//		$ordStr = hexdec( base_convert( strrev( substr( $string , $i , 2 )) , 36 , 16 ));
//		if( $j == $keyLen )
//		{
//			$j = 0;
//		}
//		$ordKey = ord( substr( $key , $j , 1 ));
//		$j++;
//		$hash .= chr( $ordStr - $ordKey );
//	}
        return hash;
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String sha1(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public static String StrRev(String str) {
        char[] strcar = str.toCharArray();
        String strret = "";
        for (int x = strcar.length - 1; x >= 0; x--) {
            strret += strcar[x];
        }
        return strret;
    }

    public static String md5(String senha) {
        String sen = "";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        sen = hash.toString(16);
        return sen;
    }

    public static JTable AjusteTab(JTable table) {

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Adiciona dados aqui

        int vColIndex = 1;
        int margin = 2;

        packColumns(table, margin);

        return table;
    }

    public static void packColumns(JTable table, int margin) {

        for (int c = 0; c < table.getColumnCount(); c++) {
            packColumn(table, c, margin);
        }
    }

// Ajusta a largura preferida da coluna visível especificada pelo vColIndex.
// A coluna será larga o bastante para mostrar o cabeçalho da coluna e a
// célula de maior conteúdo.
    public static void packColumn(JTable table, int vColIndex, int margin) {
        TableModel model = table.getModel();
        DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn col = colModel.getColumn(vColIndex);
        int width = 0; // Obtém a largura do cabeçalho da coluna
        TableCellRenderer renderer = col.getHeaderRenderer();
        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(
                table, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width; // Obtém a largura maxima da coluna de dados

        for (int r = 0; r < table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, vColIndex);
            comp = renderer.getTableCellRendererComponent(
                    table, table.getValueAt(r, vColIndex), false, false, r,
                    vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }
        width += 2 * margin; // Configura a largura
        col.setPreferredWidth(width);
    }

    public static String UnFormCpfCnpj(String cod) {

        cod = cod.replace(".", "");
        cod = cod.replace("/", "");
        cod = cod.replace("-", "");

        return cod;
    }

    public static String FormCpfCnpj(String cod) {
// TODO adicione seu código de manipulação aqui:
        if (cod == null) {
            return "";
        }
        if (cod.length() == 0) {
            return cod;
        }

        if (cod.length() == 11) {
            return cod.substring(0, 3) + "." + cod.substring(3, 6) + "." + cod.substring(6, 9) + "-" + cod.substring(9);
        } else if (cod.length() == 14) {
            return cod.substring(0, 2) + "." + cod.substring(2, 5) + "." + cod.substring(5, 8) + "/" + cod.substring(8, 12) + "-" + cod.substring(12);
        }

        return cod;
    }

    public static boolean ValidaCpf(String strCpf) {

        /* Realiza a validação do CPF.
         @param   strCPF número de CPF a ser validado
         @return  true se o CPF é válido e false se não é válido*/
        if (strCpf.trim().length() == 0) {
            return false;
        }

        int digito1, digito2, resto;
        int d1, d2;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;

        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {

            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.
            d1 = d1 + (11 - nCount) * digitoCPF;

            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        //Primeiro resto da divisão por 11.
        resto = (d1 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;

        //Segundo resto da divisão por 11.
        resto = (d2 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        //Digito verificador do CPF que está sendo validado.
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

        //Concatenando o primeiro resto com o segundo.
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
        return nDigVerific.equals(nDigResult);

    }

    public static boolean ValidaCNPJ(String str_cnpj) {

        if (str_cnpj.trim().length() == 0) {
            return false;
        }

        int soma = 0, aux, dig;
        String cnpj_calc = str_cnpj.substring(0, 12);

        if (str_cnpj.length() != 14) {
            return false;
        }

        char[] chr_cnpj = str_cnpj.toCharArray();

        /* Primeira parte */
        for (int i = 0; i < 4; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
            }
        }

        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
            }
        }

        dig = 11 - (soma % 11);

        cnpj_calc += (dig == 10 || dig == 11)
                ? "0" : Integer.toString(dig);

        /* Segunda parte */
        soma = 0;

        for (int i = 0; i < 5; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
            }
        }

        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
            }
        }

        dig = 11 - (soma % 11);

        cnpj_calc += (dig == 10 || dig == 11)
                ? "0" : Integer.toString(dig);

        return str_cnpj.equals(cnpj_calc);
    }

    public static boolean validaData(String dateStr) throws java.text.ParseException {

        if (dateStr.equals("__/__/____")) {
            return false;
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Calendar cal = new GregorianCalendar();

        // gerando o calendar
        cal.setTime(df.parse(dateStr));

        // separando os dados da string para comparacao e validacao
        String[] datac = dateStr.split("/");
        String dia = datac[0];
        String mes = datac[1];
        String ano = datac[2];

        // testando se hah discrepancia entre a data que foi
        // inserida no caledar e a data que foi passada como
        // string. se houver diferenca, a data passada era
        // invalida
        if ((new Integer(dia)).intValue() != (new Integer(cal.get(Calendar.DAY_OF_MONTH))).intValue()) {
            // dia nao casou
            return (false);
        } else if ((new Integer(mes)).intValue() != (new Integer(cal.get(Calendar.MONTH) + 1)).intValue()) {
            // mes nao casou
            return (false);
        } else if ((new Integer(ano)).intValue() != (new Integer(cal.get(Calendar.YEAR))).intValue()) {
            // ano nao casou
            return (false);
        }

        return (true);
    }

    public static String FormataData(String text) {

        int seculo = 19;

        if (text.length() < 2) {
            return "01/01/1900";
        }

        if (text.length() >= 5) {
            seculo = Integer.parseInt(lerData().substring(6, 8));

            if (Integer.parseInt(text.substring(4)) > Integer.parseInt(lerData().substring(8))) {
                seculo--;
            }
        }

        switch (text.length()) {
            case 2:
                return "0" + text.substring(0, 1) + "/0" + text.substring(1, 2) + lerData().substring(5);
            case 4:
                return text.substring(0, 2) + "/" + text.substring(2) + lerData().substring(5);
            case 5:
                return "0" + text.substring(0, 1) + "/" + text.substring(1, 3) + "/" + String.valueOf(seculo) + text.substring(4);
            case 6:
                return text.substring(0, 2) + "/" + text.substring(2, 4) + "/" + String.valueOf(seculo) + text.substring(4);
            case 7:
                return "0" + text.substring(0, 1) + "/" + text.substring(2, 4) + "/" + String.valueOf(seculo) + text.substring(4);
            case 8:
                return text.substring(0, 2) + "/" + text.substring(2, 4) + "/" + text.substring(4);
            default:
                return "  /  /    ";
        }
    }

    public static String UnFormataData(String text) {
        return text.replaceAll("/", "");
    }

    public static String VerificaNumKey(String text, java.awt.event.KeyEvent evt, boolean selall) {
        char c = evt.getKeyChar();
        if (!((c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_RIGHT) || c == KeyEvent.VK_LEFT)) {
            if (!Character.isDigit(c)) {
                text = text.substring(0, text.length());
                Toolkit.getDefaultToolkit().beep();
                evt.consume();
            } else if (selall && primdig) {
                text = "";
            }
        }
        primdig = false;
        return text;
    }

    public static String VerificaStrKey(String text, java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!((Character.isLetter(c)
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE)))) {
            text = text.substring(0, text.length());
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
        }
        return text;
    }

    public static String FormataCep(String text) {
        if (text != null) {
            if (text.trim().length() > 0) {
                return (text.substring(0, 5) + "-" + text.substring(5));
            }
        }
        return "";
    }

    public static String UnFormataCep(String text) {
        return text.replaceAll("-", "");
    }

    public static String SomaHora(String lerHora, int h, int m, int s) {
        int hor = Integer.parseInt(lerHora.substring(0, 2)) + h;
        int min = Integer.parseInt(lerHora.substring(3, 5)) + m;
        int seg = Integer.parseInt(lerHora.substring(6)) + s;
        int hc, mc;

        if (seg >= 60) {
            mc = (int) seg / 60;
            seg -= mc * 60;
            min += mc;
        }

        if (min >= 60) {
            hc = (int) min / 60;
            min -= hc * 60;
            hor += hc;
        }

        if (hor > 23) {
            hc = (int) hor / 24;
            hor -= hc * 24;
        }

        return (hor < 10 ? "0" : "") + hor + ":" + (min < 10 ? "0" : "") + min + ":" + (seg < 10 ? "0" : "") + seg;
    }

    public static String FormataHora(String text) {
        if (text.length() == 0) {
            return "00:00:00";
        }

        switch (text.length()) {
            case 2:
                return text + ":00:00";
            case 4:
                return text.substring(0, 2) + ":" + text.substring(2) + ":00";
            case 6:
                return text.substring(0, 2) + ":" + text.substring(2, 4) + ":" + text.substring(4);
            default:
                return "00:00:00";
        }
    }

    public static String UnFormataHora(String text) {
        return text.replaceAll(":", "");
    }

    public static String StatusTxt(int status) {
        switch (status) {
            case 0:
                return "Excluído Logicamente";
            case 1:
                return "Ativo";
            case 2:
                return "Ativo Não Normalizado";
            case 4:
                return "Ativo Distribuído";
            case 5:
                return "Ativo Não Normalizado Distribuído";
            case 6:
                return "Ativo Disponível Download";
            case 7:
                return "Ativo Download Efetuado";
            case 15:
                return "Encaminhado para Auditoria";
        }
        return "";
    }

    public static boolean ValidaDdd(String ddd) {
        String ddds = "00,01,02,03,04,05,06,07,08,09,10,20,23,25,26,29,30,36,39,40,50,52,56,57,58,59,60,70,72,76,78,80,90";
        if (ddd.trim().length() < 2 || ddds.contains(ddd)) {
            return false;
        }
        return true;
    }

    public static boolean ValidaCelular(String cel) {
        String cels = "62,63,65,66,67,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,91,92,93,94,95,96,97,98,99";
        if (cel.trim().length() < 8 || !cels.contains(cel.substring(0, 2))) {
            return false;
        }
        return true;
    }

    public static String FormataFone(String text) {
        if (text == null) {
            return "";
        }
        if (text.trim().length() == 0) {
            return "";
        }
        return ("(" + text.substring(0, 2) + ") " + text.substring(2, 6) + "-" + text.substring(6));
    }

    public static String FormataFoneSemDdd(String text) {
        if (text == null) {
            return "";
        }
        if (text.trim().length() == 0) {
            return "";
        }
        if (text.trim().length() == 8) {
            return (text.substring(0, 4) + "-" + text.substring(4));
        }
        if (text.trim().length() == 9) {
            return (text.substring(0, 5) + "-" + text.substring(5));
        }
        return text;
    }

    public static String UnFormataFone(String text) {

        text = text.replaceAll("-", "");
        text = text.replace("(", "");
        text = text.replace(")", "");
        text = text.replace(" ", "");
        return text;

    }

    public static Double ConvValor(String valor1) {
        double valor2;
        if (valor1.length() == 0) {
            valor2 = 0.0;
        } else {
            valor1 = valor1.replace(".", "");
            valor1 = valor1.replace(",", ".");
            valor2 = Double.parseDouble(valor1);
        }
        return valor2;
    }

    public static Float ConvValorF(String valor1) {
        float valor2;
        if (valor1.length() == 0) {
            valor2 = 0.0f;
        } else {
            valor1 = valor1.replace(".", "");
            valor1 = valor1.replace(",", ".");
            valor2 = Float.parseFloat(valor1);
        }
        return valor2;
    }

    public static String Digito(String mco) {
        int pt, pc, pd = 0, xx;
        mco += "000";
        for (xx = 0; xx < 7; xx++) {
            pd = pd + (Integer.parseInt(mco.substring(xx, xx + 1)) * (10 - (xx + 1)));
        }
        pt = ((int) (pd / 11)) + 1;
        pc = 11 * pt - pd;
        if (pc > 9) {
            pc = 0;
        }
        return String.valueOf(pc);
    }

    public static String ConvSit(char sit) {
        String str = "";
        switch (sit) {
            case 'E':
                str = "Estoque";
                break;
            case 'V':
                str = "Vendido";
                break;
            case 'C':
                str = "Caucionado";
                break;
        }
        return str;
    }

    public static char ReConvSit(String sit) {
        char str = ' ';
        if (sit.equals("Estoque")) {
            str = 'E';
        } else if (sit.equals("Vendido")) {
            str = 'V';
        } else if (sit.equals("Caucionado")) {
            str = 'C';
        }
        return str;
    }

    public static double diferencaEmDias(Date dataInicial, Date dataFinal) {
        double result = 0;
        long diferenca = dataFinal.getTime() - dataInicial.getTime();
        double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24; //resultado é diferença entre as datas em dias  
        long horasRestantes = (diferenca / 1000) / 60 / 60 % 24; //calcula as horas restantes  
        result = diferencaEmDias + (horasRestantes / 24d); //transforma as horas restantes em fração de dias  

        return result;
    }

    public static String TipoBackup(char tipo) {
        switch (tipo) {
            case '1':
                return "Inicial";
            case '2':
                return "Intermediário";
            case '3':
                return "Outro";
        }
        return "";
    }

    public static String valorPorExtenso(double vlr) {

        if (vlr == 0) {
            return ("zero");
        }
        long inteiro = (long) Math.abs(vlr);

// parte inteira do valor
        double resto = vlr - inteiro;

// parte fracionária do valor 
        String vlrS = String.valueOf(inteiro);

        if (vlrS.length() > 15) {
            return ("Erro: valor superior a 999 trilhões.");
        }

        String s = "", saux, vlrP;

        String centavos = String.valueOf((int) Math.round(resto * 100));

        String[] unidade = {"", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito",
            "nove", "dez", "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete",
            "dezoito", "dezenove"};
        String[] centena = {"", "cento", "duzentos", "trezentos",
            "quatrocentos", "quinhentos", "seiscentos", "setecentos", "oitocentos", "novecentos"};
        String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta",
            "oitenta", "noventa"};
        String[] qualificaS = {"", "mil", "milhão", "bilhão", "trilhão"};
        String[] qualificaP = {"", "mil", "milhões", "bilhões", "trilhões"};

// definindo o extenso da parte inteira do valor 
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;

        while (!vlrS.equals("0")) {
            tam = vlrS.length();

// retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789: 
// 1a. parte = 789 (centena)
// 2a. parte = 456 (mil)
// 3a. parte = 123 (milhões) 
            if (tam > 3) {
                vlrP = vlrS.substring(tam - 3, tam);
                vlrS = vlrS.substring(0, tam - 3);
            } else {
// última parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100")) {
                    saux = "cem";
                } else {
                    n = Integer.parseInt(vlrP, 10);
// para n = 371, tem-se:
                    cent = n / 100; // cent = 3 (centena trezentos) 
                    dez = (n % 100) / 10; // dez = 7 (dezena setenta) 
                    unid = (n % 100) % 10; // unid = 1 (unidade um) 
                    if (cent != 0) {
                        saux = centena[cent];
                    }
                    if ((n % 100) <= 19) {
                        if (saux.length() != 0) {
                            saux = saux + " e " + unidade[n % 100];
                        } else {
                            saux = unidade[n % 100];
                        }
                    } else {
                        if (saux.length() != 0) {
                            saux = saux + " e " + dezena[dez];
                        } else {
                            saux = dezena[dez];
                        }
                        if (unid != 0) {
                            if (saux.length() != 0) {
                                saux = saux + " e " + unidade[unid];
                            } else {
                                saux = unidade[unid];
                            }
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real) 
                    {
                        umReal = true;
                    } else {
                        saux = saux + " " + qualificaS[i];
                    }
                } else if (i != 0) {
                    saux = saux + " " + qualificaP[i];
                }
                if (s.length() != 0) {
                    s = saux + ", " + s;
                } else {
                    s = saux;
                }
            }
            if (((i == 0) || (i == 1)) && s.length() != 0) {
                tem = true; // tem centena ou mil no valor 
            }
            i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ... 
        }
        if (s.length() != 0) {
            if (umReal) {
                s = s + " real";
            } else if (tem) {
                s = s + " reais";
            } else {
                s = s + " de reais";
            }
        } // definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (s.length() != 0) // se não é valor somente com centavos 
            {
                s = s + " e ";
            }
            if (centavos.equals("1")) {
                s = s + "um centavo";
            } else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19) {
                    s = s + unidade[n];
                } else { // para n = 37, tem-se: 
                    unid = n % 10; // unid = 37 % 10 = 7 (unidade sete) 
                    dez = n / 10; // dez = 37 / 10 = 3 (dezena trinta) 
                    s = s + dezena[dez];
                    if (unid != 0) {
                        s = s + " e " + unidade[unid];
                    }
                }
                s = s + " centavos";
            }
        }
        if (s.contains("e  reais")) {
            s = s.replace("e  reais", "reais");
        }
        return (PrimeiraMaiusc(s));
    }

    public static String PrimeiraMaiusc(String txt) {
        String temp[] = txt.split(" ");
        String saida = "";
        for (int x = 0; x < temp.length; x++) {
            if (!temp[x].equals("e")) {
                temp[x] = temp[x].substring(0, 1).toUpperCase() + temp[x].substring(1);
            }
            saida += temp[x] + " ";
        }
        return saida;
    }

    public static String digitoEAN13(String num) {
        if (num.length() < 12) {
            return "X";
        }
        int somapar = 0, somaimpar = 0, result, digito = 0;
        for (int i = 0; i < num.length(); i++) {
            if (i % 2 == 0) {
                somapar += Integer.parseInt(num.substring(i, i + 1));
            } else {
                somaimpar += Integer.parseInt(num.substring(i, i + 1));
            }
        }
        result = somapar + somaimpar * 3;

        for (int i = 10; i <= 220; i += 10) {
            if (i > result) {
                digito = i - result;
                break;
            }
        }

        if (digito > 9) {
            digito = 0;
        }

        return String.valueOf(digito);
    }
}
