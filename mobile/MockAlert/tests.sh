#!/bin/sh -e

curl -i -H "Content-Type: application/hal+json" "http://localhost:8080/crise" -X POST -d '{"descricao": "Deslizamento na favela do Paraiso", "categoria": 0, "nome": "Ze das Couves", "email": "zedascouves@gmail.com", "telefone": "(12) 99876-1234", "latitude": -25.0, "longitude": -45.0, "fotografia": ""}'

curl -i -H "Content-Type: application/hal+json" "http://localhost:8080/alerta/timestamp/0/latitude/+0.5/longitude/+0.5/raio/0.5" -X GET

curl -i -H "Content-Type: application/hal+json" "http://localhost:8080/alerta/timestamp/0/latitude/+0.6/longitude/+0.6/raio/10" -X GET

curl -i -H "Content-Type: application/hal+json" "http://localhost:8080/indicadores" -X POST -d '{"indicadores": { "Cadastrados":31, "Finalizados":19, "Em andamento":6 } }'

curl -i -H "Content-Type: application/hal+json" "http://localhost:8080/indicadores" -X GET
