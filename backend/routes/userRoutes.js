'use strict';

const passport = require('passport');

module.exports = function (app) {
    var UserController = require('../controllers/userController');
    var AuthHelper = require('../helpers/authHelper');

    app.route('/users/profile')
        .get(AuthHelper.loginRequired, UserController.getProfile);

    app.route('/users')
        .get(AuthHelper.adminRequired, UserController.getAllUsers);

    app.route('/users/:id')
        .get(AuthHelper.adminRequired, UserController.getUserById)
        .delete(AuthHelper.adminRequired, UserController.deleteUser);

    app.route('/users/register')
        .post(AuthHelper.adminRequired, UserController.registerUser);

    app.route('/users/authenticate')
        .post(UserController.authenticateUser);
    
};