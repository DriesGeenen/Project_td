'use strict';

module.exports = function (app) {
    var ResultController = require('../controllers/resultController');
    var AuthHelper = require('../helpers/authHelper');

    app.route('/results')
        .get(/*AuthHelper.adminRequired, */ResultController.getAllResults)
        .post(AuthHelper.loginRequired, ResultController.addResult);

    app.route('/results/:id')
        .get(AuthHelper.adminRequired, ResultController.getResultById)
        .delete(AuthHelper.adminRequired, ResultController.deleteResult)
        .put(AuthHelper.adminRequired, ResultController.updateResult);

    app.route('/user/:userId/results')
        .get(AuthHelper.adminRequired, ResultController.getResultsByUserId);

};
