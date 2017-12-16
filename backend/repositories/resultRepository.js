'use strict';

var mongoose = require('mongoose');
var Result = mongoose.model('Result');

exports.getAllResults = function () {
    return Result.find({});
};

exports.getResultsByUserId = function (req) {
    return Result.find({user: req.params.user}).sort({date: 'desc'});
};

exports.getResultById = function (req) {
    return Result.findById({_id: req.params.id});
};

exports.addResult = function (req) {
    const newResult = new Result(req.body);
    return newResult.save();
};

exports.updateResult = function (req) {
    const newResult = new Result(req.body);
    return Result.update({_id: req.params.id}, newResult);
};

exports.deleteResult = function (req) {
    return Result.remove({_id: req.params.id});
};