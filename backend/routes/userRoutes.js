'use strict';

module.exports = function (app) {
    var UserController = require('../controllers/userController');
    var AuthHelper = require('../helpers/authHelper');

    app.route('/users/profile')
        .get(AuthHelper.loginRequired, UserController.getProfile);

    app.route('/users')
        .get(AuthHelper.logoRequired, UserController.getAllUsers);

    app.route('/users/:id')
        .get(AuthHelper.logoRequired, UserController.getUserById)
        .delete(AuthHelper.logoRequired, UserController.deleteUser);

    app.route('/users/register')
        .post(AuthHelper.logoRequired, UserController.registerUser);

    app.route('/users/authenticate')
        .post(UserController.authenticateUser);

    app.route('/logopedist/:logopedist/users')
        .get(UserController.getUsersByLogo);
};