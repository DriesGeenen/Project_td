'use strict';

var mongoose = require('mongoose');
var Reservation = mongoose.model('Reservation');

exports.getAllReservations = function () {
    return Reservation.find({});
};

exports.getReservationById = function (req) {
    return Reservation.findById({_id: req.params.id});
};

exports.addReservation = function (req) {
    const newReservation = new Reservation(req.body);
    return newReservation.save();
};

exports.updateReservation = function (req) {
    const newReservation = new Reservation(req.body);
    return Reservation.update({_id: req.params.id}, newReservation);
};

exports.deleteReservation = function (req) {
    return Reservation.remove({_id: req.params.id});
};

exports.changeReservationStatus = function (req, isConfirmed) {
    return Reservation.update({_id: req.params.id}, {isConfirmed: isConfirmed})
};

exports.getReservationsByIsConfirmed = function (req, isConfirmed) {
    return Reservation.find({isConfirmed: isConfirmed});
};

exports.getReservationsByRoomAndTimeframe = function (req) {
    const newReservation = new Reservation(req.body);
    return Reservation.find({
        $and: [
            {room: newReservation.room},
            {
                $or: [
                    {
                        // endtime overlap
                        $and: [{endtime: {$gt: newReservation.starttime}},
                            {endtime: {$lt: newReservation.endtime}}]
                    },
                    {
                        // starttime overlap
                        $and: [{starttime: {$gt: newReservation.starttime}},
                            {starttime: {$lt: newReservation.endtime}}]
                    },
                    {
                        // full overlap
                        $and: [{starttime: {$lt: newReservation.starttime}},
                            {endtime: {$gt: newReservation.endtime}}]
                    },
                    // starttime match
                    {starttime: newReservation.starttime},

                    // endtime match
                    {endtime: newReservation.endtime}
                ]
            }, {
                // ignore itself (update will be blocked without this)
                _id: {$ne: newReservation._id}
            }
        ]
    })
};