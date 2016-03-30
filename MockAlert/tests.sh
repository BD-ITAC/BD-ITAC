#!/bin/sh -e

curl -i -H "Content-Type: application/json" "http://localhost:8080/alerta" -X POST -d '{"descricaoResumida":"Alerta de deslizamento", "descricaoCompleta":"Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral", "fatorRiscoHumano":5, "fatorRiscoMaterial":5, "categoriaAlerta":0, "origemLatitude":0.50, "origemLongitude":0.50, "origemRaioKms":1.0, "destinoLatitude":0.50, "destinoLongitude":0.50, "destinoRaioKms":1.0, "endereco": ["rua das Casas", "numero das portas"]}'

curl -i -H "Content-Type: application/json" "http://localhost:8080/alerta/latitude/+0.5/longitude/+0.5/raio/1" -X GET

curl -i -H "Content-Type: application/json" "http://localhost:8080/alerta/latitude/+0.6/longitude/+0.6/raio/10" -X GET

curl -i -H "Content-Type: application/json" "http://localhost:8080/evento" -X POST -d '{"descricao": "Deslizamento na favela do Paraiso", "categoria": 0, "nome": "Ze das Couves", "email": "zedascouves@gmail.com", "telefone": "(12) 99876-1234", "endereco": ["rua das Casas", "numero das portas"]}'

curl -i -H "Content-Type: application/json" "http://localhost:8080/evento/1" -X GET

curl -i -H "Content-Type: application/json" "http://localhost:8080/evento/2" -X GET