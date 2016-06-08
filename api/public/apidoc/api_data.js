define({ "api": [
  {
    "type": "get",
    "url": "/rest/avisos",
    "title": "Listar",
    "version": "1.0.0",
    "group": "Avisos",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "list.id",
            "description": "Numero identificador."
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "list.description",
            "description": "Descrição do aviso."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "list.categoria",
            "description": "Categoria do aviso."
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "list.nome",
            "description": "Nome do usuario que enviou o aviso."
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "list.email",
            "description": "Endereço de email do usuario."
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "list.telefone",
            "description": "Telefone do usuário."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "list.latitude",
            "description": "Latitude da localização."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "list.langitude",
            "description": "Longitude da localização."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "list.cidade",
            "description": "cidade do aviso."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "list.estado",
            "description": "estado do aviso."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "list.dt",
            "description": "data e hora do aviso."
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "list.fotografia",
            "description": "Fotografia do aviso em base64."
          }
        ]
      },
      "examples": [
        {
          "title": "Sucesso",
          "content": "HTTP/1.1 200 OK\n   {\n     {\n         \"id\": 1,\n         \"descricao\" : \"Aviso de teste\",\n         \"categoria\" : 1,\n         \"nome\" : \"João da Horta\",\n         \"email\" : \"joao.horta@gmail.com\",\n         \"telefone\" : \"(12) 95678-4321\",\n         \"latitude\" : 40.0,\n         \"longitude\" : 50.0,\n         \"dt\":\"2016-06-07T18:59:44.000Z\",\n         \"cidade\":null,\n         \"estado\":null,\n         \"fotografia\" : \"....\"\n     }\n     {\n         \"id\":2,\n         \"descricao\" : \"Aviso de teste2\",\n         \"categoria\" : 2,\n         \"nome\" : \"Neusa Japonesa\",\n         \"email\" : \"japoneusa@gmail.com\",\n         \"telefone\" : \"(12) 99999-4321\",\n         \"latitude\" : 40.0,\n         \"longitude\" : 50.0,\n         \"dt\":\"2016-06-07T18:59:44.000Z\",\n         \"cidade\":null,\n         \"estado\":null,\n         \"fotografia\" : \"....\"\n     }\n  }",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "/rest/avisos"
      }
    ],
    "filename": "D:/Projetos/ITA/BD-ITAC/api/server/routes/avisos.js",
    "groupTitle": "Avisos",
    "name": "GetRestAvisos"
  },
  {
    "type": "get",
    "url": "/rest/avisos/nearbycrisis?latitude=-23.196641&longitude=-45.946840&raio=10",
    "title": "Pesquisa por coordenadas",
    "version": "1.0.0",
    "group": "Avisos",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "latitude",
            "description": "Latitude da localização."
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "longitude",
            "description": "Longitude da localização."
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "raio",
            "description": "Raio de alcance em metros."
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "descricaoResumida",
            "description": "Breve descrição do alerta."
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "descricaoCompleta",
            "description": "Descrição detalhada do alerta."
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "categoriaAlerta",
            "description": "Indica o tipo de alerta."
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "origemLatitude",
            "description": "Latitude do ponto de origem do alerta."
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "origemLongitude",
            "description": "Longitude do ponto de origem do alerta."
          },
          {
            "group": "Success 200",
            "type": "Number",
            "optional": false,
            "field": "origemRaioKms",
            "description": "Área de abrangência do alerta em Kms."
          },
          {
            "group": "Success 200",
            "type": "URI",
            "optional": false,
            "field": "_links:self:href",
            "description": "URI do link para o alerta."
          }
        ]
      },
      "examples": [
        {
          "title": "Sucesso",
          "content": "HTTP/1.1 200 OK\n{\n\t  \"_embedded\" : {\n\t    \"alertaList\" : [ {\n\t      \"descricaoResumida\" : \"Alagamento\",\n\t      \"descricaoCompleta\" : \"Deslizamento na na favela do Paraiso\",\n\t      \"categoriaAlerta\" : 0,\n\t      \"origemLatitude\" : 40.0,\n\t      \"origemLongitude\" : 50.0,\n\t      \"origemRaioKms\" : 10.0,\n\t      \"_links\" : {\n\t        \"self\" : {\n\t          \"href\" : \"http://localhost:3000/rest/crisis/nearbycrisis/16\"\n\t        }\n\t      }\n\t    }, {\n\t      \"descricaoResumida\" : \"Alagamento\",\n\t      \"descricaoCompleta\" : \"Deslizamento na na favela do Paraiso\",\n\t      \"categoriaAlerta\" : 0,\n\t      \"origemLatitude\" : 40.0,\n\t      \"origemLongitude\" : 50.0,\n\t      \"origemRaioKms\" : 10.0,\n\t      \"_links\" : {\n\t        \"self\" : {\n\t          \"href\" : \"http://localhost:3000/rest/crisis/nearbycrisis/2\"\n\t        }\n\t      }\n\t    }, {\n\t      \"descricaoResumida\" : \"Alagamento\",\n\t      \"descricaoCompleta\" : \"Crise de teste\",\n\t      \"categoriaAlerta\" : 0,\n\t      \"origemLatitude\" : 40.0,\n\t      \"origemLongitude\" : 50.0,\n\t      \"origemRaioKms\" : 10.0,\n\t      \"_links\" : {\n\t        \"self\" : {\n\t          \"href\" : \"http://localhost:3000/rest/crisis/nearbycrisis/18\"\n\t        }\n\t      }\n\t    }, {\n\t      \"descricaoResumida\" : \"Alerta de deslizamento\",\n\t      \"descricaoCompleta\" : \"Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral\",\n\t      \"categoriaAlerta\" : 0,\n\t      \"origemLatitude\" : 40.0,\n\t      \"origemLongitude\" : 50.0,\n\t      \"origemRaioKms\" : 1.0,\n\t      \"_links\" : {\n\t        \"self\" : {\n\t          \"href\" : \"http://localhost:3000/rest/crisis/nearbycrisis/4\"\n\t        }\n\t      }\n\t    }, {\n\t      \"descricaoResumida\" : \"Alerta de teste\",\n\t      \"descricaoCompleta\" : \"Teste de alerta para verificar a funcionalidade do sistema\",\n\t      \"categoriaAlerta\" : 0,\n\t      \"origemLatitude\" : 40.0,\n\t      \"origemLongitude\" : 50.0,\n\t      \"origemRaioKms\" : 1.0,\n\t      \"_links\" : {\n\t        \"self\" : {\n\t          \"href\" : \"http://localhost:3000/rest/crisis/nearbycrisis/20\"\n\t        }\n\t      }\n\t    }, {\n\t      \"descricaoResumida\" : \"Alagamento\",\n\t      \"descricaoCompleta\" : \"Deslizamento na na favela do Paraiso\",\n\t      \"categoriaAlerta\" : 0,\n\t      \"origemLatitude\" : 40.0,\n\t      \"origemLongitude\" : 50.0,\n\t      \"origemRaioKms\" : 10.0,\n\t      \"_links\" : {\n\t        \"self\" : {\n\t          \"href\" : \"http://localhost:3000/rest/crisis/nearbycrisis/6\"\n\t        }\n\t      }\n\t    }, {\n\t      \"descricaoResumida\" : \"Alagamento\",\n\t      \"descricaoCompleta\" : \"Crise de teste\",\n\t      \"categoriaAlerta\" : 0,\n\t      \"origemLatitude\" : 40.0,\n\t      \"origemLongitude\" : 50.0,\n\t      \"origemRaioKms\" : 10.0,\n\t      \"_links\" : {\n\t        \"self\" : {\n\t          \"href\" : \"http://localhost:3000/rest/crisis/nearbycrisis/8\"\n\t        }\n\t      }\n\t    }, {\n\t      \"descricaoResumida\" : \"Alerta de teste\",\n\t      \"descricaoCompleta\" : \"Teste de alerta para verificar a funcionalidade do sistema\",\n\t      \"categoriaAlerta\" : 0,\n\t      \"origemLatitude\" : 40.0,\n\t      \"origemLongitude\" : 50.0,\n\t      \"origemRaioKms\" : 1.0,\n\t      \"_links\" : {\n\t        \"self\" : {\n\t          \"href\" : \"http://localhost:3000/rest/crisis/nearbycrisis/10\"\n\t        }\n\t      }\n\t    }, {\n\t      \"descricaoResumida\" : \"Alagamento\",\n\t      \"descricaoCompleta\" : \"Deslizamento na na favela do Paraiso\",\n\t      \"categoriaAlerta\" : 0,\n\t      \"origemLatitude\" : 40.0,\n\t      \"origemLongitude\" : 50.0,\n\t      \"origemRaioKms\" : 10.0,\n\t      \"_links\" : {\n\t        \"self\" : {\n\t          \"href\" : \"http://localhost:3000/rest/crisis/nearbycrisis/12\"\n\t        }\n\t      }\n\t    }, {\n\t      \"descricaoResumida\" : \"Alerta de deslizamento\",\n\t      \"descricaoCompleta\" : \"Perigo de deslizamento na altura do Km 20 da rodovia Tamoios, pista Sao Jose dos Campos/Litoral\",\n\t      \"categoriaAlerta\" : 0,\n\t      \"origemLatitude\" : 40.0,\n\t      \"origemLongitude\" : 50.0,\n\t      \"origemRaioKms\" : 1.0,\n\t      \"_links\" : {\n\t        \"self\" : {\n\t          \"href\" : \"http://localhost:3000/rest/crisis/nearbycrisis/14\"\n\t        }\n\t      }\n\t    } ]\n\t  }\n\t}",
          "type": "json"
        }
      ]
    },
    "filename": "D:/Projetos/ITA/BD-ITAC/api/server/routes/avisos.js",
    "groupTitle": "Avisos",
    "name": "GetRestAvisosNearbycrisisLatitude23196641Longitude45946840Raio10"
  },
  {
    "type": "post",
    "url": "/rest/avisos",
    "title": "Cadastrar",
    "group": "Avisos",
    "version": "1.0.0",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "descricao",
            "description": "Descricao do aviso."
          },
          {
            "group": "Parameter",
            "type": "Numero",
            "size": "1-8",
            "optional": false,
            "field": "categoria",
            "description": "Categoria do aviso."
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "nome",
            "description": "Nome do usuário."
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "email",
            "description": "Email do usuário."
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "telefone",
            "description": "Telefone do usuário."
          },
          {
            "group": "Parameter",
            "type": "Numero",
            "optional": false,
            "field": "latitude",
            "description": "Latitude da localização."
          },
          {
            "group": "Parameter",
            "type": "Numero",
            "optional": false,
            "field": "longitude",
            "description": "Longitude da localização."
          },
          {
            "group": "Parameter",
            "type": "Numero",
            "optional": false,
            "field": "geo_id",
            "description": "id da região geográfica do aviso."
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": true,
            "field": "fotografia",
            "description": "Uma fotografia do aviso."
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n     \"descricao\" : \"Aviso de teste\",\n     \"categoria\" : \"1\",\n     \"nome\" : \"João da Horta\",\n     \"email\" : \"joao.horta@gmail.com\",\n     \"telefone\" : \"(12) 95678-4321\",\n     \"latitude\" : \"40.0\",\n     \"longitude\" : \"50.0\",\n     \"geo_id\" : \"1\",\n     \"fotografia\" : \"/9j/4AAQSkZJRgABAgAAZABkAAD/7ApZRHVja3kAAQAEAAAAPAACCkQAAAUgAE0ATwBWAEkARQBTACAAMgAwADAAMAA6ACAAIAAgACAARgBJAEwARQAtAC0AVABoAGkAcwAgAGkAcwAgAGEAbgAgAHUAbgBkAGEAdABlAGQAIABwAGgAbwB0AG8AIABvAGYAIAAnAFcAaQBsAHMAbwBuACcAIAB0AGgAZQAgAHYAbwBsAGwAeQBlAGIAYQBsAGwAIAB0AGgAYQB0ACAAawBlAHAAdAAgAFQAbwBtACAASABhAG4AawBzACcAIABjAGgAYQByAGEAYwB0AGUAcgAgAGMAbwBtAHAAYQBuAHkAIABvAG4AIABhAG4AIABpAHMAbABhAG4AZAAuACAAQQBuACAASQBuAHQAZQByAG4AZQB0ACAAcwBoAG8AcABwAGUAcgAgAGwAYQBuAGQAZQBkACAAbwBuAGUAIABvAGYAIABIAGEAbgBrACcAcwAgAGgAaQBzACAAYwBvAC0AcwB0AGEAcgBzACAAZgByAG8AbQAgAHQAaABlACAAbQBvAHYAaQBlACAAJwBDAGEAcwB0ACAAQQB3AGEAeQAuACcAIABUAGgAZQAgAHYAbwBsAGwAZQB5AGIAYQBsAGwAIAB3AGUAbgB0ACAAZgBvAHIAIAAkADEAOAAsADQAMAAwACAAdABoAGkAcwAgAHcAZQBlAGsAIABpAG4AIABhAG4AIABvAG4AbABpAG4AZQAgAGEAdQBjAHQAaQBvAG4AIABvAGYAIABtAG8AdgBpAGUAIABwAHIAbwBwAHMALAAgAHMAYQBpAGQAIABMAGkAegAgAEMAYQBzAHAAYQByAGkAcwAsACAAZABpAHIAZQBjAHQAbwByACAAbwBmACAASQBuAHQAZQByAG4AZQB0ACAAbQBhAHIAawBlAHQAaQBuAGcAIABmAG8AcgAgADIAMAB0AGgAIABDAGUAbgB0AHUAcgB5ACAARgBvAHgALgAgAFQAaABlACAAdgBvAGwAbABlAHkAYgBhAGwAbAAgAGkAcwAgAG8AbgBlACAAbwBmACAAdABoAHIAZQBlACAAJwBXAGkAbABzAG8AbgBzACcAIAB1AHMAZQBkACAAaQBuACAAdABoAGUAIABtAG8AdgBpAGUAIAB0AGgAYQB0ACAAdwBpAGwAbAAgAGIAZQAgAHMAbwBsAGQAIABvAG4AbABpAG4AZQAsACAAcwBoAGUAIABzAGEAaQBkACAAVwBlAGQAbgBlAHMAZABhAHkALgBBAGIAbwB1AHQAIAAxADUAIABpAHQAZQBtAHMAIABmAHIAbwBtACAAdABoAGUAIABmAGkAbABtACAAaABhAHYAZQAgAGIAZQBlAG4AIABzAG8AbABkACAAcwBvACAAZgBhAHIALAAgAGkAbgBjAGwAdQBkAGkAbgBnACAAYQAgAHAAYQBpAHIAIABvAGYAIABpAGMAZQAgAHMAawBhAHQAZQBzACAAdABoAGEAdAAgAHcAZQBuAHQAIABmAG8AcgAgAG0AbwByAGUAIAB0AGgAYQBuACAAJAAxACwAMAAwADAALAAgAEMAYQBzAHAAYQByAGkAcwAgAHMAYQBpAGQALgAgACAAKABBAFAAIABQAGgAbwB0AG8ALwAyADAAdABoACAAQwBlAG4AdAB1AHIAeQAgAEYAbwB4ACAAYQBuAGQAIABEAHIAZQBhAG0AdwBvAHIAawBzACAATABMAEMAKQAgACAAIAAgACAAIAAtAC0ATwByAGkAZwBpAG4AYQBsACAASQBQAFQAQwAgAEkAbgBmAG8AcgBtAGEAdABpAG8AbgA6ACAAIAAgAEMAYQBwAHQAaQBvAG4AOgAgAEYASQBMAEUALQAtAFQAaABpAHMAIABpAHMAIABhAG4AIAB1AG4AZABhAHQAZQBkACAAcABoAG8AdABvACAAbwBmACAAJwBXAGkAbABzAG8AbgAnACAAdABoAGUAIAB2AG8AbABsAHkAZQBiAGEAbABsACAAdABoAGEAdAAgAGsAZQBwAHQAIABUAG8AbQAgAEgAYQBuAGsAcwAnACAAYwBoAGEAcgBhAGMAdABlAHIAIABjAG8AbQBwAGEAbgB5ACAAbwBuACAAYQBuACAAaQBzAGwAYQBuAGQALgAgAEEAbgAgAEkAbgB0AGUAcgBuAGUAdAAgAHMAaABvAHAAcABlAHIAIABsAGEAbgBkAGUAZAAgAG8AbgBlACAAbwBmACAASABhAG4AawAnAHMAIABoAGkAcwAgAGMAbwAtAHMAdABhAHIAcwAgAGYAcgBvAG0AIAB0AGgAZQAgAG0AbwB2AGkAZQAgACcAQwBhAHMAdAAgAEEAdwBhAHkALgAnACAAVABoAGUAIAB2AG8AbABsAGUAeQBiAGEAbABsACAAdwBlAG4AdAAgAGYAbwByACAAJAAxADgALAA0ADAAMAAgAHQAaABpAHMAIAB3AGUAZQBrACAAaQBuACAAYQBuACAAbwBuAGwAaQBuAGUAIABhAHUAYwB0AGkAbwBuACAAbwBmACAAbQBvAHYAaQBlACAAcAByAG8AcABzACwAIABzAGEAaQBkACAATABpAHoAIABDAGEAcwBwAGEAcgBpAHMALAAgAGQAaQByAGUAYwB0AG8AcgAgAG8AZgAgAEkAbgB0AGUAcgBuAGUAdAAgAG0AYQByAGsAZQB0AGkAbgBnACAAZgBvAHIAIAAyADAAdABoACAAQwBlAG4AdAB1AHIAeQAgAEYAbwB4AC4AIABUAGgAZQAgAHYAbwBsAGwAZQB5AGIAYQBsAGwAIABpAHMAIABvAG4AZQAgAG8AZgAgAHQAaAByAGUAZQAgACcAVwBpAGwAcwBvAG4AcwAnACAAdQBzAGUAZAAgAGkAbgAgAHQAaABlACAAbQBvAHYAaQBlACAAdABoAGEAdAAgAHcAaQBsAGwAIABiAGUAIABzAG8AbABkACAAbwBuAGwAaQBuAGUALAAgAHMAaABlACAAcwBhAGkAZAAgAFcAZQBkAG4AZQBzAGQAYQB5AC4AIABBAGIAbwB1AHQAIAAxADUAIABpAHQAZQBtAHMAIABmAHIAbwBtACAAdABoAGUAIABmAGkAbABtACAAaABhAHYAZQAgAGIAZQBlAG4AIABzAG8AbABkACAAcwBvACAAZgBhAHIALAAgAGkAbgBjAGwAdQBkAGkAbgBnACAAYQAgAHAAYQBpAHIAIABvAGYAIABpAGMAZQAgAHMAawBhAHQAZQBzACAAdABoAGEAdAAgAHcAZQBuAHQAIABmAG8AcgAgAG0AbwByAGUAIAB0AGgAYQBuACAAJAAxACwAMAAwADAALAAgAEMAYQBzAHAAYQByAGkAcwAgAHMAYQBpAGQALgAgACgAQQBQACAAUABoAG8AdABvAC8AMgAwAHQAaAAgAEMAZQBuAHQAdQByAHkAIABGAG8AeAAgAGEAbgBkACAARAByAGUAYQBtAHcAbwByAGsAcwAgAEwATABDACkAAP/uAA5BZG9iZQBkwAAAAAH/2wCEAAYEBAQFBAYF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\"\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "id",
            "description": "Numero identificador do aviso cadastrada"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "type",
            "description": "Tipo da mensagem (INFO, WARNING, ERROR)"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "status",
            "description": "Código de estado do sistema"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "description",
            "description": "Descrição da mensagem"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "info",
            "description": "Informações adicionais"
          }
        ]
      },
      "examples": [
        {
          "title": "Sucesso",
          "content": "HTTP/1.1 201 Created\n   {\n  \"message\" : {\n      \"id\" : 1,\n      \"type\" : \"INFO\",\n      \"status\" : \"OK\",\n      \"description\" : \"Crise registrada\",\n      \"info\" : \"BD-ITAC\"\n    }\n  }",
          "type": "json"
        }
      ]
    },
    "filename": "D:/Projetos/ITA/BD-ITAC/api/server/routes/avisos.js",
    "groupTitle": "Avisos",
    "name": "PostRestAvisos"
  },
  {
    "type": "get",
    "url": "/rest/categories",
    "title": "Listar",
    "version": "1.0.0",
    "group": "Categorias",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "categories.id",
            "description": "Numero identificador."
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "categories.description",
            "description": "Descrição do aviso."
          }
        ]
      },
      "examples": [
        {
          "title": "Sucesso",
          "content": "HTTP/1.1 200 OK\n   {\n     {\n         \"id\": \"1\",\n         \"descricao\" : \"Alagamentos\"\n     }\n     {\n         \"id\":\"2\",\n         \"descricao\" : \"Emanação vulcânica\"\n     }\n  }",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "/rest/categories"
      }
    ],
    "filename": "D:/Projetos/ITA/BD-ITAC/api/server/routes/category.js",
    "groupTitle": "Categorias",
    "name": "GetRestCategories"
  },
  {
    "type": "get",
    "url": "/rest/crisis/indicators",
    "title": "Indicadores de crises",
    "version": "1.0.0",
    "group": "Crisis",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "cadastrados",
            "description": "Quantidade de crises cadastradas."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "finalizados",
            "description": "Quantidade de crises finalizadas."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "emcurso",
            "description": "Quantidade de crises em curso."
          }
        ]
      },
      "examples": [
        {
          "title": "Sucesso",
          "content": "HTTP/1.1 200 OK\n  {\n       cadastrados: 30,\n       finalizados : 20,\n       emcurso : 8\n  }",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "/rest/crisis/indicators"
      }
    ],
    "filename": "D:/Projetos/ITA/BD-ITAC/api/server/routes/avisos.js",
    "groupTitle": "Crisis",
    "name": "GetRestCrisisIndicators"
  },
  {
    "type": "get",
    "url": "/rest/crisis/type",
    "title": "Palavras Chaves de Crises",
    "version": "1.0.0",
    "group": "Crisis",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "c",
            "description": "ódigo do tipo/palavra da crise."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "ctp_id",
            "description": "código do tipo do sub-tipo da crise."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "ctt_id",
            "description": "código do tipo do tipo da crise."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "cts_id",
            "description": "código do tipo de subgrupo da crise."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "ctg_id",
            "description": "código do tipo de grupo da crise."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "ctc_id",
            "description": "código do tipo da classe da crise."
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "descri",
            "description": "ção do tipo da crise."
          }
        ]
      },
      "examples": [
        {
          "title": "Sucesso",
          "content": "HTTP/1.1 200 OK\n  {\n       ctr_id: 3,\n       ctp_id : 2,\n       ctt_id : 8,\n       cts_id : 8,\n       ctg_id : 8,\n       ctc_id : 8,\n       ctr_descricao : \"TESTE TESTE\",\n       ctr_definicao : \"TESTE TESTE\",\n       ctr_cobrade : \"TESTE TESTE\",\n  }",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "/rest/crisis/type"
      }
    ],
    "filename": "D:/Projetos/ITA/BD-ITAC/api/server/routes/crisis.js",
    "groupTitle": "Crisis",
    "name": "GetRestCrisisType"
  }
] });
