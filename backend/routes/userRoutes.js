'use strict';

module.exports = function (app) {
    var UserController = require('../controllers/userController');
    var AuthHelper = require('../helpers/authHelper');

    app.route('/users')
        .get(AuthHelper.adminRequired, UserController.getAllUsers);

    app.route('/users/register')
        .post(UserController.registerUser);

    app.route('/users/authenticate')
        .post(UserController.authenticateUser);

    app.route('/users/profile')
        .get(AuthHelper.loginRequired, UserController.getProfile);
};