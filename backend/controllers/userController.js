'use strict';

var UserRepository = require('../repositories/userRepository');
const User = require('../models/user');
const bcrypt = require('bcryptjs');
const config = require('../config/database');
const jwt = require('jsonwebtoken');

exports.getAllUsers = function (req, res) {
    var promise = UserRepository.getAllUsers(req);
    promise.then(function (users) {
        res.json(users);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get users', error: err});
    });
    console.log('getAll: ' + JSON.stringify(req.user.data));
};

exports.getUserById = function (req, res) {
    var promise = UserRepository.getUserById(req);
    promise.then(function (result) {
        res.json(result);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get result', error: err});
    });
};

exports.deleteUser = function (req, res) {
    var promise = UserRepository.deleteUser(req);
    promise.then(function () {
        res.json({success: true, msg: 'User removed'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to remove result', error: err});
    });
};


// Extra functions

exports.registerUser = function (req, res) {
    const newUser = new User(req.body);
    var promise = bcrypt.genSalt(10);
    promise.then(function (salt) {
        return bcrypt.hash(newUser.password, salt);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to create user', error: err});
    }).then(function (hash) {
        req.body.password = hash;
        return UserRepository.addUser(req);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to create user', error: err});
    }).then(function () {
        res.json({success: true, msg: 'User created'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to create user', error: err});
    });
};

exports.authenticateUser = function (req, res) {
    const email = req.body.email;
    const password = req.body.password;
    var user;

    var promise = UserRepository.getUserByEmail(email);
    promise.then(function (usr) {
        if (!usr) {
            res.status(404).json({success: false, msg: 'User not found'});
        }
        user = usr;
        return bcrypt.compare(password, usr.password);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get user', error: err});
    }).then(function (isMatch) {
        if (isMatch) {
            const token = jwt.sign({data: user}, config.secret, {
                expiresIn: 604800
            });
            res.json({
                success: true,
                token: 'JWT ' + token,
                user: user
            });
        }
        else {
            res.status(403).json({success: false, msg: 'Wrong password'});
        }
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to match passwords', error: err});
    });
};

exports.getProfile = function (req, res) {

    res.json({user: req.user.data})
};

exports.getUsersByLogo = function(req, res) {
    var promise = UserRepository.getUsersByLogo(req);
    promise.then(function (result) {
        res.json(result);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get users', error: err});
    });
}