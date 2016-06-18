package br.ita.bditac.ws.model;

import android.graphics.Bitmap;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Crise implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String descricao;

    private int categoria;
    
    private String nome;
    
    private String email;
    
    private String telefone;

    private double latitude;

    private double longitude;

    private byte[] fotografia;
    
    public Crise() {
        this.id = 0;
        
        this.descricao = "";
        this.categoria = 0;
        this.nome = "";
        this.email = "";
        this.telefone = "";
        this.fotografia = null;
    }
    
    public Crise(
            String descricao,
            int categoria,
            String nome,
            String email,
            String telefone,
            double latitude,
            double longitude,
            Bitmap bitmap) {
        this.id = 0;
        
        this.descricao = descricao;
        this.categoria = categoria;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.latitude = latitude;
        this.longitude = longitude;
        if(bitmap == null) {
            this.fotografia = "".getBytes();
        }
        else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            this.fotografia = outputStream.toByteArray();
        }
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCategoria() {
        return categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getFotografia() {
        String encodeString = new String(Base64.encodeBase64(fotografia));
        return encodeString.replace("+", "-").replace("/", "_");
    }

}
