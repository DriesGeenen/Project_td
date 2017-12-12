'use strict';

var mongoose = require('mongoose');
var User = mongoose.model('User');

exports.getAllUsers = function (req) {
    return User.find({});
};

exports.getUserById = function (id) {
    return User.findById(id);
};

exports.getUserByEmail = function (email) {
    return User.findOne({username:email});
};

exports.addUser = function (req) {
    const newUser = new User(req.body);
    return newUser.save();
};


