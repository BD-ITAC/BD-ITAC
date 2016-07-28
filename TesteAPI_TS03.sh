#!/bin/sh -e

## Listar as Categorias (ok)
curl -i -H "Content-Type: application/hal+json" "http://200.144.14.28/rest/categories" -X GET

## Listar os Indicadores de Crise (ok)
curl -i -H "Content-Type: application/hal+json" "http://200.144.14.28/rest/avisos/indicators" -X GET

## Listar todas os Alertas Cadastrados (ok)
curl -i -H "Content-Type: application/hal+json" "http://200.144.14.28/rest/avisos" -X GET

## Listar o Alerta pelo ID
## Exite (OK)
curl -i -H "Content-Type: application/hal+json" "http://200.144.14.28/rest/avisos/1" -X GET
curl -i -H "Content-Type: application/hal+json" "http://200.144.14.28/rest/avisos/2" -X GET

## Não existe (NOK, pois não retorna nada)
curl -i -H "Content-Type: application/hal+json" "http://200.144.14.28/rest/avisos/3" -X GET
curl -i -H "Content-Type: application/hal+json" "http://200.144.14.28/rest/avisos/4" -X GET

## Cadastrar Aviso com foto (ok)
curl 'http://200.144.14.28/rest/avisos' -H "Content-Type: application/json" -d '{"descricao":"Teste Ts02 - com foto","categoria":"1","nome":"ts02","email":"ts02@gmail.com","telefone":"(12) 95678-4321","latitude":"40.0","longitude":"50.0","fotografia":"77+9UE5HDQoaCgAAAA1JSERSAAAADAAAABAIBgAAACJh77+9BwAAAARnQU1BAADvv73vv70L77+9YQUAAAAgY0hSTQAAeiYAAO+/ve+/vQAA77+9AAAA77+977+9AAB1MAAA77+9YAAAOu+/vQAAF3Dvv73vv71RPAAAAAlwSFlzAAAXEgAAFxIBZ++/ve+/vVIAAAFZaVRYdFhNTDpjb20uYWRvYmUueG1wAAAAAAA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJYTVAgQ29yZSA1LjQuMCI+CiAgIDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+CiAgICAgIDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiCiAgICAgICAgICAgIHhtbG5zOnRpZmY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vdGlmZi8xLjAvIj4KICAgICAgICAgPHRpZmY6T3JpZW50YXRpb24+MTwvdGlmZjpPcmllbnRhdGlvbj4KICAgICAgPC9yZGY6RGVzY3JpcHRpb24+CiAgIDwvcmRmOlJERj4KPC94OnhtcG1ldGE+Ckzvv70nWQAAAk5JREFUKBVVUk1PE1EUPe+/ve+/ve+/vTDvv73vv71K77+9UEPvv71R77+90JTvv73vv70qau+/vQ0bE1bvv70FfwF/77+977+9QNyx77+9aEx077+9RkNY77+977+9BRpFTTDvv700FAUtae+/vX7vv71H77+9M++/vTMlanzvv71577+977+977+9e8+977+9au+/ve+/vVfvv70577+977+9aSLvv71KYVZo77+9z6ZlYe+/ve+/ve+/ve+/vV4XB++/ve+/ve+/ve+/ve+/ve+/vSgpFO+/vSfvv71OJDB377+9xIfvv70tVCPvv73vv73vv70yRu+/vWYR77+977+9Je+/vQbvv71B77+977+9SgU/X2/vv73vv73vv73vv71f77+977+977+9RzDvv71dRe+/vTjvv707DRpkS0rvv70e77+9MO+/vXAD77+9du+/ve+/ve+/ve+/vX3vv71yWVw6b++/vX1C77+977+9bDrvv73vv71ZEu+/ve+/ve+/vUYTRwTvv70/X++/ve+/ve+/vQLvv70uMGoa77+977+9Pj7vv70DEu+/vTEE77+9EO+/ve+/vS8w77+977+9Ru+/vXQF77+977+977+977+977+9dT3vv73vv70577+9BnBJEGDvv71cIt6C77+9JO+/vSbvv73vv73vv73ktIPvv71NzZrvv73vv73vv70EbhoD77+9YO+/vWYMZCIJIe+/vX3vv70WVnQHIgxQ77+977+9EEbvv70w3rjvv70jFe+/vRkM77+977+977+977+9DQZ4Mj3vv73vv73vv71777+9M++/ve+/vULvv71YJgLvv71F77+977+9Z++/ve+/vRAmJe+/ve+/vSJNEwHvv70LTO+/vXJiGk/vv70S77+9H2BAae+/vUXvv73vv70ifu+/vRbvv71E77+977+977+9Ue+/vSfvv73vv71F77+9Mu+/ve+/vRRL77+9IGnvv73vv71F77+977+977+9xLZIIEfvv73vv70x77+9HGlw77+977+977+9Imnvv73vv73vv71xbFzvv73vv73vv70477+977+9KxU6BO+/ve+/vXfvv70jdknvv73Qp3nvv716HTPvv73vv73vv718CXXvv73vv73vv73vv70GbO+/ve+/ve+/ve+/vRHvv70exqckGicgORrvv73vv73vv70o77+9L++/ve+/ve+/vU7vv70SRu+/vVfvv70R77+9AdG5NBTvv73vv73vv73vv70G77+9Lnd777+9f++/vUfvv73vv73VqlUYDO+/vWwk77+9Le+/vTTvv71zWu+/ve+/ve+/vVPvv73vv70MDu+/ve+/ve+/ve+/vT0+77+977+977+9Q++/ve+/vT4K77+9RSAzDu+/ve+/vXHvv73GtVgnzYt+A0fvv73vv70T77+9AO+/vWUAAAAASUVORO+/vUJg77+9"}'

## Cadastrar Aviso sem foto (ok)
curl 'http://200.144.14.28/rest/avisos' -H "Content-Type: application/json" -d '{"descricao":"Teste TS02 - sem foto","categoria":"1","nome":"ts02","email":"ts02@gmail.com","telefone":"(12) 95678-4321","latitude":"40.0","longitude":"50.0","fotografia":""}'

## Listar as Aviso pela posição GPS (ok)
curl -i -H "Content-Type: application/hal+json" "http://200.144.14.28/rest/avisos/nearbyWarnings?latitude=-23&longitude=-45&raio=10&timestamp='2016/06/10T08:00:00'" -X GET

curl -i -H "Content-Type: application/hal+json" "http://200.144.14.28/rest/avisos/nearbyWarnings?latitude=40&longitude=50&raio=10&timestamp='2016/06/10T08:00:00'" -X GET
