'use strict';

module.exports = function (app) {
    var RoomController = require('../controllers/roomController');
    var AuthHelper = require('../helpers/authHelper');

    app.route('/rooms')
        .get(RoomController.getAllRooms)
        .post(AuthHelper.adminRequired, RoomController.addRoom);

    app.route('/rooms/:id')
        .get(RoomController.getRoomById)
        .delete(AuthHelper.adminRequired, RoomController.deleteRoom)
        .put(AuthHelper.adminRequired, RoomController.updateRoom);

    app.route('/rooms/reservations/:from/:to')
        .get(RoomController.getAllRoomsWithReservationsByTimespan);

    app.route('/rooms/:id/reservations/:from/:to')
        .get(RoomController.getRoomWithReservationsByTimespan);
};
