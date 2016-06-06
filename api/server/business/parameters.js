module.exports = function(app){
  var business = {};
  var paramDAO = app.dao.parameters;

  business.deleteClassificacao = function(key, callback)
  {
    if(!key)
    {
      callback({success: false, message: 'Invalid value data fields.', validationError:true});
    }
    else
    {
        paramDAO.deleteClassificacao(key, function(err){
        if(err){
          console.log('Deleting parameter error:' + err);
          callback({success: false, message: err});
        }else{
          callback(null);
        }
      });
    }

  };
  business.deleteEvento = function(key, callback)
  {
    if(!key)
    {
      callback({success: false, message: 'Invalid value data fields.', validationError:true});
    }
    else
    {
        paramDAO.deleteEvento(key, function(err){
        if(err){
          console.log('Deleting parameter error:' + err);
          callback({success: false, message: err});
        }else{
          callback(null);
        }
      });
    }

  };
  /**
  * Realiza registro de uma classificação
  * @author Edizon
  *
  */
  business.saveClassificacao = function(classfic, callback){
    if(!classfic.descricao)
    {
      callback({success: false, message: 'Invalid value data fields.', validationError:true});
    }
    else
    {
      paramDAO.saveClassificacao(classfic, function(err, result){
          if(err){
            callback({success: false, message: err}, null);
          }else{
            callback(null, result);
          }
      });
    }
   };
   business.saveEvento = function(evento, callback){
     if(!evento.descricao)
     {
       callback({success: false, message: 'Invalid value data fields.', validationError:true});
     }
     else
     {
       paramDAO.saveEvento(evento, function(err, result){
           if(err){
             callback({success: false, message: err}, null);
           }else{
             callback(null, result);
           }
       });
     }
    };

return business;

};
