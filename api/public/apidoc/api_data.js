define({ "api": [
  {
    "type": "get",
    "url": "/rest/crisis",
    "title": "Listar",
    "version": "1.0.0",
    "group": "Crisis",
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
            "description": "Descrição da evento."
          },
          {
            "group": "Success 200",
            "type": "Numero",
            "optional": false,
            "field": "list.categoria",
            "description": "Categoria da evento."
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "list.nome",
            "description": "Nome do usuario que enviou a evento."
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
            "type": "String",
            "optional": false,
            "field": "list.fotografia",
            "description": "Fotografia do evento em base64."
          }
        ]
      },
      "examples": [
        {
          "title": "Sucesso",
          "content": "HTTP/1.1 200 OK\n   {\n     {\n         \"id\": 1,\n         \"descricao\" : \"Crise de teste\",\n         \"categoria\" : 1,\n         \"nome\" : \"João da Horta\",\n         \"email\" : \"joao.horta@gmail.com\",\n         \"telefone\" : \"(12) 95678-4321\",\n         \"latitude\" : 40.0,\n         \"longitude\" : 50.0,\n         \"fotografia\" : \"....\"\n     }\n     {\n         \"id\":2,\n         \"descricao\" : \"Crise de teste2\",\n         \"categoria\" : 2,\n         \"nome\" : \"Neusa Japonesa\",\n         \"email\" : \"japoneusa@gmail.com\",\n         \"telefone\" : \"(12) 99999-4321\",\n         \"latitude\" : 40.0,\n         \"longitude\" : 50.0,\n         \"fotografia\" : \"....\"\n     }\n  }",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "/rest/crisis"
      }
    ],
    "filename": "C:/desenvolvimento/git/BD-ITAC/server/routes/crisis.js",
    "groupTitle": "Crisis",
    "name": "GetRestCrisis"
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
    "filename": "C:/desenvolvimento/git/BD-ITAC/server/routes/crisis.js",
    "groupTitle": "Crisis",
    "name": "GetRestCrisisIndicators"
  },
  {
    "type": "get",
    "url": "/rest/crisis/nearbycrisis?latitude=40.0&longitude=50.0&raio=1.0",
    "title": "Pesquisa por coordenadas",
    "version": "1.0.0",
    "group": "Crisis",
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
            "description": "Raio de alcance."
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
    "filename": "C:/desenvolvimento/git/BD-ITAC/server/routes/crisis.js",
    "groupTitle": "Crisis",
    "name": "GetRestCrisisNearbycrisisLatitude400Longitude500Raio10"
  },
  {
    "type": "post",
    "url": "/rest/crisis",
    "title": "Cadastrar",
    "group": "Crisis",
    "version": "1.0.0",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "descricao",
            "description": "Descricao da crise."
          },
          {
            "group": "Parameter",
            "type": "Numero",
            "size": "1-8",
            "optional": false,
            "field": "categoria",
            "description": "Categoria da crise."
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
            "type": "String",
            "optional": true,
            "field": "fotografia",
            "description": "Uma fotografia da crise."
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:",
          "content": "{\n   \"descricao\" : \"Crise de teste\",\n   \"categoria\" : 0,\n   \"nome\" : \"João da Horta\",\n   \"email\" : \"joao.horta@gmail.com\",\n   \"telefone\" : \"(12) 95678-4321\",\n   \"latitude\" : 40.0,\n   \"longitude\" : 50.0,\n   \"fotografia\" : \"77+9UE5HDQoaCgAAAA1JSERSAAAADAAAABAIBgAAACJh77+9BwAAAARnQU1BAADvv73vv70L77+9YQUAAAAgY0hSTQAAeiYAAO+/ve+/vQAA77+9AAAA77+977+9AAB1MAAA77+9YAAAOu+/vQAAF3Dvv73vv71RPAAAAAlwSFlzAAAXEgAAFxIBZ++/ve+/vVIAAAFZaVRYdFhNTDpjb20uYWRvYmUueG1wAAAAAAA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJYTVAgQ29yZSA1LjQuMCI+CiAgIDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+CiAgICAgIDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiCiAgICAgICAgICAgIHhtbG5zOnRpZmY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vdGlmZi8xLjAvIj4KICAgICAgICAgPHRpZmY6T3JpZW50YXRpb24+MTwvdGlmZjpPcmllbnRhdGlvbj4KICAgICAgPC9yZGY6RGVzY3JpcHRpb24+CiAgIDwvcmRmOlJERj4KPC94OnhtcG1ldGE+Ckzvv70nWQAAAk5JREFUKBVVUk1PE1EUPe+/ve+/ve+/vTDvv73vv71K77+9UEPvv71R77+90JTvv73vv70qau+/vQ0bE1bvv70FfwF/77+977+9QNyx77+9aEx077+9RkNY77+977+9BRpFTTDvv700FAUtae+/vX7vv71H77+9M++/vTMlanzvv71577+977+977+9e8+977+9au+/ve+/vVfvv70577+977+9aSLvv71KYVZo77+9z6ZlYe+/ve+/ve+/ve+/vV4XB++/ve+/ve+/ve+/ve+/ve+/vSgpFO+/vSfvv71OJDB377+9xIfvv70tVCPvv73vv73vv70yRu+/vWYR77+977+9Je+/vQbvv71B77+977+9SgU/X2/vv73vv73vv73vv71f77+977+977+9RzDvv71dRe+/vTjvv707DRpkS0rvv70e77+9MO+/vXAD77+9du+/ve+/ve+/ve+/vX3vv71yWVw6b++/vX1C77+977+9bDrvv73vv71ZEu+/ve+/ve+/vUYTRwTvv70/X++/ve+/ve+/vQLvv70uMGoa77+977+9Pj7vv70DEu+/vTEE77+9EO+/ve+/vS8w77+977+9Ru+/vXQF77+977+977+977+977+9dT3vv73vv70577+9BnBJEGDvv71cIt6C77+9JO+/vSbvv73vv73vv73ktIPvv71NzZrvv73vv73vv70EbhoD77+9YO+/vWYMZCIJIe+/vX3vv70WVnQHIgxQ77+977+9EEbvv70w3rjvv70jFe+/vRkM77+977+977+977+9DQZ4Mj3vv73vv73vv71777+9M++/ve+/vULvv71YJgLvv71F77+977+9Z++/ve+/vRAmJe+/ve+/vSJNEwHvv70LTO+/vXJiGk/vv70S77+9H2BAae+/vUXvv73vv70ifu+/vRbvv71E77+977+977+9Ue+/vSfvv73vv71F77+9Mu+/ve+/vRRL77+9IGnvv73vv71F77+977+977+9xLZIIEfvv73vv70x77+9HGlw77+977+977+9Imnvv73vv73vv71xbFzvv73vv73vv70477+977+9KxU6BO+/ve+/vXfvv70jdknvv73Qp3nvv716HTPvv73vv73vv718CXXvv73vv73vv73vv70GbO+/ve+/ve+/ve+/vRHvv70exqckGicgORrvv73vv73vv70o77+9L++/ve+/ve+/vU7vv70SRu+/vVfvv70R77+9AdG5NBTvv73vv73vv73vv70G77+9Lnd777+9f++/vUfvv73vv73VqlUYDO+/vWwk77+9Le+/vTTvv71zWu+/ve+/ve+/vVPvv73vv70MDu+/ve+/ve+/ve+/vT0+77+977+977+9Q++/ve+/vT4K77+9RSAzDu+/ve+/vXHvv73GtVgnzYt+A0fvv73vv70T77+9AO+/vWUAAAAASUVORO+/vUJg77+9\"\n }",
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
            "description": "Numero identificador da crise cadastrada"
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
    "filename": "C:/desenvolvimento/git/BD-ITAC/server/routes/crisis.js",
    "groupTitle": "Crisis",
    "name": "PostRestCrisis"
  }
] });
