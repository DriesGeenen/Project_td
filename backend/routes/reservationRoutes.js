'use strict';

module.exports = function (app) {
    var ReservationController = require('../controllers/reservationController');
    var AuthHelper = require('../helpers/authHelper');

    app.route('/reservations')
        .get(ReservationController.getAllReservations)
        .post(AuthHelper.loginRequired, ReservationController.addReservation);

    app.route('/reservations/confirmed')
        .get(ReservationController.getConfirmedReservations);

    app.route('/reservations/unconfirmed')
        .get(ReservationController.getUnconfirmedReservations);

    app.route('/reservations/:id')
        .get(ReservationController.getReservationById)
        .delete(AuthHelper.adminRequired, ReservationController.deleteReservation)
        .put(AuthHelper.loginRequired, ReservationController.updateReservation);

    app.route('/reservations/:id/confirm')
        .patch(AuthHelper.adminRequired, ReservationController.confirmReservation);

    app.route('/reservations/:id/deny')
        .patch(AuthHelper.adminRequired, ReservationController.deleteReservation);
};
