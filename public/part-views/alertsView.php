<?php 
    // Start MySQL Connection
    include('../server/db/transacao.js'); 
?>

<html>
<head>
    <title>Situation Room</title>
    <style type="text/css">
        .table_titles, .table_cells_odd, .table_cells_even {
                padding-right: 20px;
                padding-left: 20px;
                color: #000;
        }
        .table_titles {
            color: #FFF;
            background-color: #666;
        }
        .table_cells_odd {
            background-color: #CCC;
        }
        .table_cells_even {
            background-color: #FAFAFA;
        }
        table {
            border: 2px solid #333;
        }
        body { font-family: "Trebuchet MS", Arial; }
    </style>
</head>

    <body>
        <h1>Alerts List</h1>
    <table border="0" cellspacing="0" cellpadding="7">
      <tr>
            <td class="table_titles">nome</td>
            <td class="table_titles">descrição</td>
			
      </tr>
<?php
    // Retrieve all records and display them  // AQUI MOSTRA DE QUAL TABELA
    $result = mysql_query("SELECT nome,descricao FROM crisis ORDER BY nome ASC");

    // Used for row color toggle
    $oddrow = true;

    // process every record
    while( $row = mysql_fetch_array($result) )
    {
        if ($oddrow) 
        { 
            $css_class=' class="table_cells_odd"'; 
        }
        else
        { 
            $css_class=' class="table_cells_even"'; 
        }
 
        $oddrow = !$oddrow;

        echo '<tr>';
        echo '   <td'.$css_class.'>'.$row["nome"].'</td>';
        echo '   <td'.$css_class.'>'.$row["descricao"].'</td>';
       
		
    }
?>
    </table>
    </body>
   
</html>
