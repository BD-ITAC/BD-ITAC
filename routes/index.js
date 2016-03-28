
/*
 * GET pages ejs

 */

exports.index = function(req, res){
  res.render('index', { title: 'Hello World' });
};

exports.one = function(req, res){
  res.render('part-views/one', { title: 'Hello World' });
};
exports.three = function(req, res){
  res.render('part-views/three', { title: 'Hello World' });
};
