<?php
	$cidades = array ("São José dos Campos-SP","Ubatuba-SP", "Salvador-BA", "Recife-PE", "Curitiba-PR");
	
	//mostras as cidades para coleta	
	echo "<h4>As cidades que serão coletadas as previsões de tempo são: </h4>";
	foreach ($cidades as $value) {
    	echo "$value<br />\n";
	}	
	
	//criando linhas para formatação
	echo "<br><br>";
	//funcao que retorna o codigo da cidade, buscando a cidade pedida diferenciando das homonimas
	function ache_cidade($lista_xml_cidades, $dados_cidade) {
		
		foreach ($lista_xml_cidades as $possivel_cidade) {
			if (($possivel_cidade->nome == $dados_cidade[2]) && ($possivel_cidade->uf == $dados_cidade[1])) {
					$cidade_codigo = $possivel_cidade->id;
					return $cidade_codigo;	
			}
   	
		}
		
	}
	
	//funcao que remove acentos, para que o nome da cidade seja aceito no xml 
	function stripAccents($str) {
    	return strtr(utf8_decode($str), utf8_decode('àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ'), 'aaaaaceeeeiiiinooooouuuuyyAAAAACEEEEIIIINOOOOOUUUUY');
}
	
	//coleta e processamento
	foreach ($cidades as $i => $value) {
		
		//separa a cidade do estado 
		$cidade_estado = explode("-", $cidades[$i]); 
		
			
		//adiciondo o nome da cidade original no fim da string
		array_push($cidade_estado, $cidade_estado[0]);
		
		//trata acentos e espaços para formar a urL
		$cidade_estado[0] = stripAccents($cidade_estado[0]);
		$cidade_estado[0] = rawurlencode($cidade_estado[0]);
		
		
		//busca o codigo da cidade
		$xml_cidade = simplexml_load_file("http://servicos.cptec.inpe.br/XML/listaCidades?city=".$cidade_estado[0]);
	
		//se achar mais de uma cidade, identificar qual é a cidade solicitada
		$cidade_id = ache_cidade($xml_cidade,$cidade_estado);
		
		 
		//pega a pevisao
		$xml_previsao = simplexml_load_file("http://servicos.cptec.inpe.br/XML/cidade/".$cidade_id."/previsao.xml");
		$json_output = json_encode($xml_previsao, JSON_UNESCAPED_UNICODE);
		echo "<b>Resultado da Coleta em JSON da cidade de ".$cidade_estado[2].":</b>";
		echo "<pre>".$json_output."</pre><br />\n";

		}
?>

