'use strict';

var ReservationRepository = require('../repositories/reservationRepository');
var nodemailer = require('nodemailer');


var transporter = nodemailer.createTransport({
    service: 'gmail',//Dit is de mail-server die gebruikt wordt door de verzender van de mail
    auth: {
        user: 'lguacademygroep7@gmail.com',//Hier moet het e-mailadres van de organisatie ingevuld worden tussen de aanhalingstekens
        pass: 'LGUGroep7'//Hier moet het wachtwoord van het e-mailadres worden ingevuld tussen de aanhalingstekens
    }
});
var reservationMail = nodemailer.createTransport({
    service: 'gmail', //Dit is de mail-server die gebruikt wordt door de verzender van de mail
    auth: {
        user: 'lgugebruikergroep7@gmail.com',//Hier moet het e-mailadres van de gebruiker ingevuld worden tussen de aanhalingstekens
        pass: 'LGUGroep7'//Hier moet het wachtwoord van het e-mailadres worden ingevuld tussen de aanhalingstekens
    }
});
var mailOptionsFail = {
    from: 'lguacademygroep7@gmail.com',
    to: 'lgugebruikergroep7@gmail.com',
    subject: 'Jouw reservatie',
    text: 'Jouw reservatie voor een zaal bij LGU werd geweigerd :( better luck next time!'
};

var mailOptionsSuccess = {
    from: 'lguacademygroep7@gmail.com',
    to: 'lgugebruikergroep7@gmail.com',
    subject: 'Jouw reservatie',
    text: 'Jouw reservatie voor een zaal bij LGU werd geaccepteerd!'
};

var mailOptionsUpdated = {
    from: 'lguacademygroep7@gmail.com',
    to: 'lgugebruikergroep7@gmail.com',
    subject: 'Jouw reservatie',
    text: 'Jouw reservatie voor een zaal bij LGU werd aangepast!'
};

var mailOptionsToAdmin = {
    from: 'lgugebruikergroep7@gmail.com',
    to: 'lguacademygroep7@gmail.com',
    subject: 'Nieuwe reservatie',
    text: 'Er is een nieuwe reservatie gemaakt!'
};


var RoomRepository = require('../repositories/roomRepository');


exports.getAllReservations = function (req, res) {
    var promise = ReservationRepository.getAllReservations();
    promise.then(function (reservations) {
        res.json(reservations);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get reservations', error: err});
    });
};

exports.getReservationById = function (req, res) {
    var promise = ReservationRepository.getReservationById(req);
    promise.then(function (reservation) {
        res.json(reservation);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get reservation', error: err});
    });
};

exports.addReservation = function (req, res) {
    var promise = ReservationRepository.getReservationsByRoomAndTimeframe(req);
    promise.then(function (reservations) {
        if (Object.size(reservations) !== 0) {
            res.status(403).json({success: false, msg: 'Room not available for reservation'})
        } else {
            reservationMail.sendMail(mailOptionsToAdmin);
            return ReservationRepository.addReservation(req);
        }
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to check availability', error: err});
    }).then(function (reservation) {
        return RoomRepository.addReservationToRoom(reservation);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to create reservation', error: err});
    }).then(function () {
        res.json({success: true, msg: 'Reservation created'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to add reservation to room', error: err});
    });


};

exports.updateReservation = function (req, res) {
    var promise = ReservationRepository.getReservationsByRoomAndTimeframe(req);
    promise.then(function (reservations) {
        if (Object.size(reservations) !== 0) {
            res.status(403).json({success: false, msg: 'Room not available for reservation'})
        } else {
            transporter.sendMail(mailOptionsUpdated);
            return ReservationRepository.updateReservation(req);
        }
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to check availability', error: err});
    }).then(function () {
        res.json({success: true, msg: 'Reservation updated'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to update reservation', error: err});
    });


};

exports.deleteReservation = function (req, res) {
    var promise = ReservationRepository.deleteReservation(req);
    promise.then(function () {
        transporter.sendMail(mailOptionsFail);
        res.json({success: true, msg: 'Reservation removed'});

        promise.then(function (reservation) {
            return RoomRepository.removeReservationFromRoom(reservation);

        }, function (err) {
            res.status(500).json({success: false, msg: 'Failed to remove reservation', error: err});
        }).then(function () {
            res.json({success: true, msg: 'Reservation removed'});
        }, function (err) {
            res.status(500).json({success: false, msg: 'Failed to remove reservation from room', error: err});
        });
    });
};

// Extra functions
exports.confirmReservation = function (req, res) {
    var promise = ReservationRepository.changeReservationStatus(req, true);
    promise.then(function () {
        transporter.sendMail(mailOptionsSuccess);
        res.json({success: true, msg: 'Reservation status updated'});
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to change reservation status', error: err});
    });
};

exports.getUnconfirmedReservations = function (req, res) {
    var promise = ReservationRepository.getReservationsByIsConfirmed(req, false);
    promise.then(function (reservations) {
        res.json(reservations);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get reservations', error: err});
    });
};

exports.getConfirmedReservations = function (req, res) {
    var promise = ReservationRepository.getReservationsByIsConfirmed(req, true);
    promise.then(function (reservations) {
        res.json(reservations);
    }, function (err) {
        res.status(500).json({success: false, msg: 'Failed to get reservations', error: err});
    });
};


Object.size = function (obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};