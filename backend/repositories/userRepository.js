'use strict';

var mongoose = require('mongoose');
var User = mongoose.model('User');

exports.getAllUsers = function (req) {
    return User.find({});
};

exports.getUserById = function (id) {
    return User.findById(id);
};

exports.getUserByUsername = function (username) {
    return User.findOne({username:username});
};

exports.addUser = function (req) {
    const newUser = new User(req.body);
    return newUser.save();
};


