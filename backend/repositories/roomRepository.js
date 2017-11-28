'use strict';

var mongoose = require('mongoose');
var Room = mongoose.model('Room');

exports.getAllRooms = function () {
    return Room.find({});
};

exports.getRoomById = function (req) {
    return Room.findById({_id: req.params.id});
};

exports.addRoom = function (req) {
    const newRoom = new Room(req.body);
    return newRoom.save();
};

exports.updateRoom = function (req) {
    const newRoom = new Room(req.body);
    return Room.update({_id: req.params.id}, newRoom);
};

exports.deleteRoom = function (req) {
    return Room.remove({_id: req.params.id});
};

exports.getAllRoomsWithReservationsByTimespan = function (req) {
    return Room.find().populate({
        path: 'reservations',
        match: {$and: [{endtime: {$gte: req.params.from}}, {starttime: {$lte: req.params.to}}]}
    });
};

exports.getRoomByIdWithReservationsByTimespan = function (req) {
    return Room.findById({_id: req.params.id}).populate({
        path: 'reservations',
        match: {$and: [{endtime: {$gte: req.params.from}}, {starttime: {$lte: req.params.to}}]}
    });
};

exports.addReservationToRoom = function (reservation) {
    return Room.findOneAndUpdate({_id: reservation.room}, {$push: {reservations: reservation}});
};

exports.removeReservationFromRoom = function (reservation) {
    return Room.findOneAndUpdate({_id: reservation.room}, {$pull: {reservations: reservation}});
};