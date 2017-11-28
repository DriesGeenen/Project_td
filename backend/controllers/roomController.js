'use strict';

var RoomRepository = require('../repositories/roomRepository');
var ReservationRepository = require('../repositories/reservationRepository');

exports.getAllRooms = function (req, res) {
    var promise = RoomRepository.getAllRooms();
    promise.then(function (rooms) {
        res.json(rooms);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get rooms', error: err});
    });
};

exports.getRoomById = function (req, res) {
    var promise = RoomRepository.getRoomById(req);
    promise.then(function (room) {
        res.json(room);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get room', error: err});
    });
};

exports.addRoom = function (req, res) {
    var promise = RoomRepository.addRoom(req);
    promise.then(function () {
        res.json({success: true, msg: 'Room created'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to create room', error: err});
    });
};

exports.updateRoom = function (req, res) {
    var promise = RoomRepository.updateRoom(req);
    promise.then(function () {
        res.json({success: true, msg: 'Room updated'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to update room', error: err});
    });
};

exports.deleteRoom = function (req, res) {
    var promise = RoomRepository.deleteRoom(req);
    promise.then(function () {
        res.json({success: true, msg: 'Room removed'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to remove room', error: err});
    });
};

// extra functions

exports.getAllRoomsWithReservationsByTimespan = function (req, res) {
    var promise = RoomRepository.getAllRoomsWithReservationsByTimespan(req);
    promise.then(function (rooms) {
        res.json(rooms);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get rooms', error: err});
    });
};

exports.getRoomWithReservationsByTimespan = function (req, res) {
    var promise = RoomRepository.getRoomByIdWithReservationsByTimespan(req);
    promise.then(function (room) {
        res.json(room);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get room', error: err});
    });
};