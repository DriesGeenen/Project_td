'use strict';

var ResultRepository = require('../repositories/resultRepository');

exports.getAllResults = function (req, res) {
    var promise = ResultRepository.getAllResults();
    promise.then(function (results) {
        res.json(results);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get results', error: err});
    });
};

exports.getResultsByUserId = function (req, res) {
    var promise = ResultRepository.getResultsByUserId(req);
    promise.then(function (results) {
        res.json(results);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get results', error: err});
    });
};

exports.getResultById = function (req, res) {
    var promise = ResultRepository.getResultById(req);
    promise.then(function (result) {
        res.json(result);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get result', error: err});
    });
};

exports.addResult = function (req, res) {
    // remember which user posted the result
    req.body.user = req.user.data._id;
    // set date to today
    req.body.date = new Date();

    console.log(req.body);
    var promise = ResultRepository.addResult(req);
    promise.then(function () {
        res.json({success: true, msg: 'Result created'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to create result', error: err});
    });
};

exports.updateResult = function (req, res) {
    var promise = ResultRepository.updateResult(req);
    promise.then(function () {
        res.json({success: true, msg: 'Result updated'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to update result', error: err});
    });
};

exports.deleteResult = function (req, res) {
    var promise = ResultRepository.deleteResult(req);
    promise.then(function () {
        res.json({success: true, msg: 'Result removed'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to remove result', error: err});
    });
};
