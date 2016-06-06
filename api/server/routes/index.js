module.exports = function(app){

    /*app.route('/:url(api|auth|components|app|bower_components|assets)/*').get(function(req, res){
      res.render('error');
  });*/

    app.route('/').get(function(req, res) {
      res.render('index', { title: 'BD-ITAC - Situation Room' });
    });

};
